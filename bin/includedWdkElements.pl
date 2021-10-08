#!/usr/bin/perl

use strict;

use LWP::Simple;

use JSON;

use Data::Dumper;
use Getopt::Long;

my ($help, $checkParams, $unidbWebsite, $componentSitePrefix);

&GetOptions('help|h' => \$help,
            'check_params' => \$checkParams,
            'unidb_website=s' => \$unidbWebsite,
            'component_website_prefix=s' => \$componentSitePrefix
    );

my $UNIDB_WEBSITE = $unidbWebsite ? $unidbWebsite : "beta.veupathdb.org";

my  @websites = ("plasmodb.org",
                 "amoebadb.org", 
                 "cryptodb.org",
                 "giardiadb.org",
                 "hostdb.org",
                 "microsporidiadb.org",
                 "piroplasmadb.org",
                 "toxodb.org",
                 "tritrypdb.org",
                 "trichdb.org",
                 "vectorbase.org",
                 "fungidb.org",
);

push(@websites, $UNIDB_WEBSITE);

if($componentSitePrefix) {
  @websites = map { $componentSitePrefix . "." . $_ } @websites;
}

my $results = {};

foreach my $website (@websites) {
  print STDERR "Reading from website:  $website\n";

  my $baseUrl = "https://${website}/a";

  my $recordTypesUrl = $baseUrl . "/service/record-types";
  my $recordTypes = &getUrlContentAsHash($recordTypesUrl);

  foreach my $recordType (@$recordTypes) {
    print STDERR "Collecting search and record data for:  $recordType\n";
    my $searchesUrl = $recordTypesUrl . "/" . $recordType . "/searches";
    my $searches =  &getUrlContentAsHash($searchesUrl);
    &addSearchesToResults($results, $recordType, $searches, $website);

    my $recordUrl = $recordTypesUrl . "/" . $recordType;
    my $record =  &getUrlContentAsHash($recordUrl);

    &addRecordAttributesToResults($results, $recordType, $record, $website);
    &addRecordTablesToResults($results, $recordType, $record, $website);
  }
}

print STDERR "Finished Collecting Search and Record Data\n";


foreach my $recordType (keys %{$results}) {

  if($checkParams) {
    print STDERR "Reading parameter values from $recordType searches\n";

    &checkParamsForRecordType($results, $recordType);
  }

  foreach my $category (keys %{$results->{$recordType}}) {
    foreach my $name (keys %{$results->{$recordType}->{$category}}) {

      # lots of auto generated attributes for transcripomics impossible to test this way
      next if($category eq 'recordAttributes' and $name =~ /^pan_/); 

      my $inUniDB = $results->{$recordType}->{$category}->{$name}->{unidb};
      my $inComponent = $results->{$recordType}->{$category}->{$name}->{component};


      my $status = $inUniDB == $inComponent ? "Ok" : "Error";
      
      print "$recordType\t$category\t$name\t$inComponent\t$inUniDB\t$status\n";

    }
  }
}

sub checkParamsForRecordType {
  my ($results, $recordType) = @_;

  return unless($recordType eq 'transcript');

  my @searchNames = keys %{$results->{$recordType}->{searches}};
  foreach my $searchName (@searchNames) {
    my ($shortSearchName) = $searchName =~ /\.([^\.]+)$/;

    my @websitesContainingSearch = grep { !/component|unidb/ } keys %{$results->{$recordType}->{searches}->{$searchName}};

    my $paramResults = {};
    my $searchPageError;

    foreach my $website (@websitesContainingSearch) {
      print STDERR "WEBSITE=$website\n";

      my $searchUrl = "https://${website}/a/service/record-types/${recordType}/searches/$shortSearchName";

      my $search;
      eval {
        $search =  &getUrlContentAsHash($searchUrl);
      };
      if($@) {
        print "$recordType\tsearchs_ERROR\t${searchUrl}\t\tError\n";
        $searchPageError = 1;
      }

      my $parameters = $search->{searchData}->{parameters};
      if($parameters && scalar @$parameters > 0) {
        &addParamValuesToResults($parameters, $paramResults, $website);
      }
    }

    &checkParamValues($searchName, $paramResults, $recordType) unless($searchPageError); # no reason to report here if the page was broken
  }
}


sub addRecordTablesToResults {
  my ($results, $recordType, $record, $website) = @_;

  my $recordTables = $record->{tables};
  my @tableNames = map { $_->{name} } @$recordTables;
  foreach my $tableName (@tableNames) {
    if($website eq $UNIDB_WEBSITE) {
      $results->{$recordType}->{recordTables}->{$tableName}->{unidb} = 1;
    }
    else {
      $results->{$recordType}->{recordTables}->{$tableName}->{component} = 1;
    }
  }
}


sub addRecordAttributesToResults {
  my ($results, $recordType, $record, $website) = @_;

  my $recordAttributes = $record->{attributes};
  my @attributeNames = map { $_->{name} } @$recordAttributes;

  foreach my $attributeName (@attributeNames) {
    if($website eq $UNIDB_WEBSITE) {
      $results->{$recordType}->{recordAttributes}->{$attributeName}->{unidb} = 1;
    }
    else {
      $results->{$recordType}->{recordAttributes}->{$attributeName}->{component} = 1;
    }
  }
}

sub addSearchesToResults {
  my ($results, $recordType, $searches, $website) = @_;

  my @searchNames = map { $_->{fullName} } @$searches;

  foreach my $searchName (@searchNames) {
    $results->{$recordType}->{searches}->{$searchName}->{$website} = 1;

    if($website eq $UNIDB_WEBSITE) {
      $results->{$recordType}->{searches}->{$searchName}->{unidb} = 1;
    }
    else {
      $results->{$recordType}->{searches}->{$searchName}->{component} = 1;
    }
  }
}

sub checkParamValues {
  my ($searchName, $paramResults, $recordType) = @_;

  my $category = "parameter_value";

  foreach my $paramName (keys %{$paramResults->{_params}}) {

    if($paramResults->{_depended_params}->{$paramName}) {
      print STDERR "SKIPPING Param $paramName for search $searchName because it depends on another param\n";
      next;
    }

    foreach my $paramValue (keys %{$paramResults->{_params}->{$paramName}}) {
      my $inUniDB = $paramResults->{_params}->{$paramName}->{$paramValue}->{unidb};
      my $inComponent = $paramResults->{_params}->{$paramName}->{$paramValue}->{component};
      
      my $status = $inUniDB == $inComponent ? "Ok" : "Error";

      print "$recordType\t$category\t${searchName}:${paramName}:${paramValue}\t$inComponent\t$inUniDB\t$status\n";

    }
  }
}


sub addParamValuesToResults {
  my ($parameters, $paramResults, $website) = @_;


  foreach my $param (@$parameters) {
    my $name = $param->{name};


    foreach(@{$param->{dependentParams}}) {
      $paramResults->{_depended_params}->{$_} = 1;
    }

    if($param->{displayType} eq 'treeBox') {
    }
    else {
      foreach my $a (@{$param->{vocabulary}}) {
        my $val = $a->[0]; # guessing this is the term but it may be $a->[1];

        if($website eq $UNIDB_WEBSITE) {
          $paramResults->{_params}->{$name}->{$val}->{unidb} = 1;
        }
        else {
          $paramResults->{_params}->{$name}->{$val}->{component} = 1;
        }
      }

    }
  }
}

sub getUrlContentAsHash {
  my ($url) = @_;

  my $content = get $url;
  die "Couldn't get $url" unless defined $content;
  
  return decode_json $content;
}




