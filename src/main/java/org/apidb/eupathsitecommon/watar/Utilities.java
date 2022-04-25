package org.apidb.eupathsitecommon.watar;

public class Utilities {
    public static final String PAGE_LOAD_TIME = "PageLoadTime";
  
    public static final String GENE_RECORD_PATH_TMPL = "/app/record/gene/";
    public static final String WSF_PATH = "/service";

    public static final String SRT_TOOL = "/app/fasta-tool";
  
    public static final String GENE_MODEL_CHARS_SEARCH = "/app/search/transcript/GenesByGeneModelChars";
    public static final String GENES_BY_TAXON_SEARCH = "/app/search/transcript/GenesByTaxon";
    public static final String GENES_BY_LOCUS_SEARCH = "/app/search/transcript/GeneByLocusTag";
    public static final String ORGANISM_RESULTS = "/app/search/organism/GenomeDataTypes/result";
    public static final String DATASET_RESULTS = "/app/search/dataset/AllDatasets/result";
  
    public static final String ALL_SEARCHES = "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig=%7B\"attributes\"%3A%5B\"primary_key\"%5D%2C\"tables\"%3A%5B\"References\"%5D%7D\"";
    public static final String DATASETS_SEARCHES = "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig=%7B\"attributes\"%3A%5B\"primary_key\"%5D%2C\"tables\"%3A%5B%5D%7D";
    public static final String TRACK_KEYS = "/service/jbrowse/tracks/default/trackList.json?v=0.34781133727929614";
    public static final String GENOME_DATA_TYPES = "/service/record-types/organism/searches/GenomeDataTypes/reports/standard?reportConfig=%7B%22attributes%22:[%22primary_key%22,%22rnaseqcount%22],%22tables%22:[],%22attributeFormat%22:%22text%22%7D";  
    public static final String RNA_SEQ_PROFILE = "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig=%7B\"attributes\"%3A%5B\"primary_key\"%5D%2C\"tables\"%3A%5B\"RNASeqSamplesInternal\"%5D%7D\"";
    public static final String CHECK_DATABASE_CATEGORY = "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig=%7B%22attributes%22%3A%5B%22primary_key%22%2C%22newcategory%22%5D%7D%22";
    public static final String GENES_WITH_USER_COMMENTS = "/app/search/transcript/GenesWithUserComments";

    public static final String TESTING_URL = "https://jbrestel.plasmodb.org/plasmo.jbrestel/service/record-types/legacy-dataset/searches/Datasets/reports/standard?reportConfig=%7B%22attributes%22%3A%5B%22primary_key%22%5D%2C%22tables%22%3A%5B%5D%2C%22attributeFormat%22%3A%22text%22%7D&auth_tkt=NGE1YTVkMzFhMDcwMjczMWZjYjY2Nzc5NzY5ZmQ1NDY2MjY2YWE5YmFwaWRiIWFwaWRiITE2NTA4OTU1MTU6";
    public static final String PRODUCTION_DATASETS = "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig={%22attributes%22%3A[%22primary_key%22]%2C%22tables%22%3A[%22Version%22]}%22";
    
    // Community
    public static final String NEWS_PATH = "/app/static-content/PlasmoDB/news.html";
    public static final String RELATED_SITES = "/app/static-content/PlasmoDB/externalLinks.html";
  
    // Publications
    public static final String PUBLICATIONS = "/app/static-content/veupathPubs.html";
    
    // Submit Data
    public static final String DATA_SUBMISSION = "/app/static-content/dataSubmission.html";
    public static final String ABOUT = "/app/static-content/about.html";
    public static final String PRIVACY_POLICY = "/app/static-content/privacyPolicy.html";
    public static final String PERSONNEL = "/app/static-content/personnel.html";
    public static final String ACKS = "/app/static-content/acks.html";
  
    public static final String METHODS = "/app/static-content/methods.html";
    public static final String INFRASTRUCTURE = "/app/static-content/infrastructure.html";

    public static final String LOGIN_PAGE = "https://eupathdb.org/auth/bin/autologin";
  
}
