#!/usr/bin/perl
use strict; 
use DBI;
use DBD::Oracle;
use XML::Simple;
use Data::Dumper;


use Getopt::Long;

use strict;

my ($help, $outDir, $instanceSuffix);


&GetOptions('help|h' => \$help,
            'dir=s' => \$outDir,
            'instance_suffix=s' => \$instanceSuffix,
    );

my $outfile = "results.1";
while(-e "$outDir/$outfile") {
  my $suffixRegex = qr /\.(\d+)/;
  my ($suffix) = $outfile =~ $suffixRegex;
  $suffix = $suffix + 1;

  $suffix = "." . $suffix;
  $outfile =~ s/$suffixRegex/$suffix/;
}

open(OUT, ">$outDir/$outfile") or die "Cannot open file $outfile for writing: $!";

my $done = {};
foreach my $f (glob $outDir . "/*") {
  open(IN, $f) or die "Cannot open file $f for reading: $!";
  while(<IN>) {
    chomp;
    my ($instance, $table, $count) = split(/\t/, $_);

    $done->{$table}->{$instance} = $count;
  }
  close IN;
}

my @queryFiles = glob "ApiCommonModel/Model/lib/wdk/model/records/*Queries.xml";

my $dbs = [{instance => "plas${instanceSuffix}", model => "PlasmoDB"},
           {instance => "ameb${instanceSuffix}", model => "AmoebaDB"},
           {instance => "cryp${instanceSuffix}", model => "CryptoDB"},
           {instance => "giar${instanceSuffix}", model => "GiardiaDB"},
           {instance => "host${instanceSuffix}", model => "HostDB"},
           {instance => "micr${instanceSuffix}", model => "MicrosporidiaDB"},
           {instance => "piro${instanceSuffix}", model => "PiroplasmaDB"},
           {instance => "toxo${instanceSuffix}", model => "ToxoDB"},
           {instance => "tryp${instanceSuffix}", model => "TriTrypDB"},
           {instance => "tvag${instanceSuffix}", model => "TrichDB"},
           {instance => "vect${instanceSuffix}", model => "VectorBase"},
           {instance => "fung${instanceSuffix}", model => "FungiDB"},
           {instance => "uni${instanceSuffix}", model => "UniDB"},

    ];




my %results;

foreach my $db (@$dbs) {
  my $instance = $db->{instance};
  my $model = $db->{model};

  print STDERR "Start Instance $instance\n";

  my $dbh = DBI->connect("dbi:Oracle:$instance") or die DBI->errstr;
  $dbh->{LongReadLen} = 512 * 1024; 
  $dbh->{RaiseError} = 1;
  $dbh->{AutoCommit} = 0;

  foreach(@queryFiles) {

    my $xml = XMLin($_, ForceArray => 1, KeyAttr => undef);


    foreach my $querySet (@{$xml->{querySet}}) {
      my $querySetName = $querySet->{name};

      foreach my $sqlQuery (@{$querySet->{sqlQuery}}) {
        my $sqlQueryName = $sqlQuery->{name};

        my $queryFullName = $querySetName . "." . $sqlQueryName;

        my $includeProjects = $sqlQuery->{includeProjects};
        my $excludeProjects = $sqlQuery->{excludeProjects};


        if(&isQueryIncluded($includeProjects, $excludeProjects, $model)) {
          next if(defined($done->{$queryFullName}->{$instance}));

          if($queryFullName eq 'GeneTables.PhenotypeTable' ||
             $queryFullName eq 'SequenceAttributes.Bfmv' ||
             $queryFullName eq 'SequenceTables.SequencePieces' ||
             $queryFullName eq 'OrganismTables.GenomeSequencingAndAnnotation' ||
             $queryFullName eq 'OrganismTables.Searches' ||
             $queryFullName eq 'PopsetTables.BioMaterialCharacteristics' ||
             $queryFullName eq 'PopsetTables.HtsContacts' ||
             $queryFullName eq 'PopsetTables.GeneOverlap' ||
             $queryFullName eq 'SnpAttributes.Bfmv' ||
             $queryFullName eq 'TranscriptAttributes.TranscriptsFoundPerGene' ||
             $queryFullName eq 'TranscriptAttributes.Bfmv' ||
             $queryFullName eq 'TranscriptAttributes.HasProteomics' ||
             $queryFullName eq 'TranscriptAttributes.DnaGTracks' ||
             $queryFullName eq 'TranscriptAttributes.GeneDBOrganism' ||
             $queryFullName eq 'TranscriptAttributes.MetaProfileSamplescparIowaII_rtpcr_Kissinger_RtPcr_RSRC' ||
             $queryFullName eq 'GeneTables.NcrassaPhenotypeImages' ||
             $queryFullName eq 'GeneTables.MercatorAlignment' ||
             $queryFullName eq 'GeneTables.Product' ||
             $queryFullName eq 'GeneTables.PreferredProducts' ||
             $queryFullName eq 'GeneTables.MetabolicPathwayReactions' ||
             $queryFullName eq 'GeneTables.FacetMetadata' ||
             $queryFullName eq 'GeneTables.ContXAxisMetadata' ||
             $queryFullName eq 'GeneTables.Sequences' ||
             $queryFullName =~ /Graphs$/ ||
             $queryFullName =~ /^Orf/) {
            print STDERR "WARN: Skipping query: $queryFullName\n";
            next;
          }

          my $sql = $sqlQuery->{sql}->[0];

          $sql =~ s/\@PROJECT_ID\@/$model/g;
          next if($sql =~ /\@/); # skip all other macros
          next if($sql =~ /\#\#WDK_ID_SQL\#\#/); # skip wdk special
          next if($sql =~ /\$\$/); # skip param values

          print STDERR "RUNNING $queryFullName\n";
          my $sh = $dbh->prepare("select count(*) from ( $sql )");
          $sh->execute();
          my ($count) = $sh->fetchrow_array();
          $sh->finish();

          print OUT "$instance\t$queryFullName\t$count\n";

          $done->{$queryFullName}->{$instance} = $count;
        }
      }
    }

  }

  $dbh->disconnect();
}

close OUT;


my @instances = map { $_->{instance} } @{$dbs};

print "NAME\t" . join("\t", @instances) . "\n";

foreach my $name (keys %{$done}) {
  my @values = map { $done->{$name}->{$_} } @instances;

  print $name . "\t" . join("\t", @values) . "\n";
}

sub isQueryIncluded {
  my ($include, $exclude, $name) = @_;

  if($include) {
    if($include =~ /$name/) {
      return 1;
    }
    else {
      return 0;
    }
  }
  if($exclude) {
    if($exclude =~ /$name/) {
      return 0;
    }
  }
  return 1;
}


1;

