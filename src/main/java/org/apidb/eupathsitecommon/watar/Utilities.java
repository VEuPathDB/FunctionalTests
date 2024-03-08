package org.apidb.eupathsitecommon.watar;

import java.util.Arrays;
import java.util.List;

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

    public static final String LEGACY_DATASETS = "/service/record-types/legacy-dataset/searches/Datasets/reports/standard?reportConfig=%7B%22attributes%22%3A%5B%22primary_key%22%5D%2C%22tables%22%3A%5B%5D%2C%22attributeFormat%22%3A%22text%22%7D";
    // SitemapDatasets give all public datasets for a site
    public static final String PRODUCTION_DATASETS = "/service/record-types/dataset/searches/SitemapDatasets/reports/standard?reportConfig={%22attributes%22%3A[%22primary_key%22]%2C%22tables%22%3A[%22Version%22]}%22";
    public static final String MAPPING_TABLE = "/service/record-types/dataset/searches/AllDatasets/reports/standard?reportConfig={%22attributes%22%3A[%22primary_key%22]%2C%22tables%22%3A[%22DatasetAlias%22]}";
    
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
    
    // Testing Failures
    
    public static final List<String> failedDatasetIds = Arrays.asList("DS_012298da2c","DS_043fe19169","DS_0732741900","DS_079607ca4d","DS_08fe07cd15","DS_0a27b6bb7c","DS_0b93ddce0d","DS_0bd5c9ce25","DS_0bfe431e88","DS_0d4dd969f2","DS_0d9e5a3aaa","DS_0e0a6f3045","DS_0e6a419642","DS_0f19274214","DS_10bf5b155d","DS_114ffa1e42","DS_11cc0909dd","DS_12a4b65dcc","DS_13662120eb","DS_135c202e2f","DS_1373afc053","DS_1479d1c65e","DS_16012a9b37","DS_166e521549","DS_1742c0cd90","DS_1871ed8e0c","DS_18d27de505","DS_1907ef0f34","DS_1cc763e9d0","DS_1cd846fd00","DS_1ddb8babd8","DS_1d84ebd027","DS_1e92f1af90","DS_213496374f","DS_21c6a7b5fe","DS_2216592524","DS_243bc178f0","DS_24b68cb68a","DS_268bc9d797","DS_269cf62506","DS_289bdd71a3","DS_2a0a90eba6","DS_2a7b5a1ada","DS_2d13238b19","DS_2db3d0872d","DS_2dd5798f04","DS_3045197830","DS_325f546077","DS_32b4753324","DS_33b107b983","DS_3371d4c2e0","DS_354fdff56a","DS_36e9d31613","DS_37d4189c5a","DS_3f3de1f403","DS_3ef9c215b7","DS_3fcd3d4602","DS_3fc04e7ff1","DS_4267c95a1c","DS_426f7ac5d5","DS_4307cfb7f2","DS_42de5c0884","DS_4477b85a6c","DS_46ca1ca0a7","DS_486c6c20b9","DS_48bf0699e2","DS_4a1633821f","DS_4a9649ea09","DS_4bcd3fd7f4","DS_4cb8e12fb2","DS_4db802da0d","DS_4e72fffb36","DS_4eb29e611f","DS_5771fd6126","DS_58470a7950","DS_588b223e96","DS_59d1c30302","DS_5a67784cef","DS_5b592b4487","DS_5b7bc44266","DS_5c4175a5a1","DS_5bf278ebf7","DS_5cc278b3c9","DS_5dfd0d0bb2","DS_5ea853774b","DS_60f232b2f3","DS_61df67732f","DS_633427c0ea","DS_649c2a1004","DS_64b7b206a5","DS_64eb488423","DS_65779f34e0","DS_66ab196f50","DS_66e2695658","DS_672da12264","DS_69ab031ad9","DS_6a99014266","DS_6f53a4a049","DS_7022bb9d95","DS_7042aff55e","DS_71148e3658","DS_71f2c2118b","DS_7209159db1","DS_74b55e5a49","DS_76baca140e","DS_776abb5d52","DS_773518f5a9","DS_77fc236061","DS_7cc80562ca","DS_7df59d2fa4","DS_7e2bd6d15f","DS_7e69303d47","DS_8106d16029","DS_826ab8e41c","DS_8422fbca1c","DS_8360a983e6","DS_85a463de8b","DS_8711baff19","DS_88ee7df4f1","DS_8f297c8245","DS_8fde6fa47c","DS_8f674deb8c","DS_90d577fd5f","DS_90dc58ff6f","DS_90eea17ef6","DS_91c53e61a3","DS_91b3e4633f","DS_931b25e522","DS_9436654ff3","DS_93ead824d4","DS_98b6bcc3d8","DS_9906c5c8f5","DS_9a7f849906","DS_9aff260283","DS_9b88b7f1a3","DS_9bccd43ed7","DS_9c4bcfb00e","DS_9fbe24e98a","DS_a184b311a9","DS_a1665c1735","DS_a0e51f125a","DS_a104c5de28","DS_a83e08c9d9","DS_a9eb7e00a0","DS_aa581c04fc","DS_ac2a27fcc5","DS_acb52ee82f","DS_ac6c552bd5","DS_aed6c3e0df","DS_afec111616","DS_b1a056b0b3","DS_b23482fada","DS_b244ca2a77","DS_b462cceac1","DS_b46b0509cf","DS_b4b53f9435","DS_b522a98769","DS_b4d3d960fc","DS_b596003550","DS_b5913e0a1e","DS_b57833f9f4","DS_b7b6cff99c","DS_b7ec60f469","DS_b8755b3393","DS_b7fe1066d2","DS_b8f73726ef","DS_baf9ed83c4","DS_baca73415b","DS_bc5b0b299e","DS_be912cb3cd","DS_be78b1682c","DS_bf7eb4c5c2","DS_c1919ed48f","DS_c334877c42","DS_c351e9a043","DS_c357287e29","DS_c3b1287080","DS_c557df700f","DS_c6151731ef","DS_c7ef4d99b1","DS_c87b905ece","DS_c9a2d71a32","DS_c968c64b08","DS_ca480bb83f","DS_caec2bd9bb","DS_cc66146b8b","DS_cc23accd38","DS_cd7b17d580","DS_ce6b5bc42d","DS_cf5baf39bd","DS_d05d77c803","DS_d12e9b5e9a","DS_d1c8287de9","DS_d273af8882","DS_d27c9dd420","DS_d33a14b7e6","DS_d45f4a2849","DS_d630a9d906","DS_d6339f85c9","DS_d7d1b2b1ce","DS_d8246ca26a","DS_d8fd7514d2","DS_d9cbcddc1a","DS_dc734e89d3","DS_dd01ba6e6d","DS_de635f87f8","DS_df3742f4a3","DS_e03933e8eb","DS_e04154f473","DS_e0774345f1","DS_e19cdf0a8c","DS_e23b9209c1","DS_e39356a5a6","DS_e41006ac89","DS_e4b88a1be6","DS_e51cb08950","DS_e97975c145","DS_eb1f1d3a5c","DS_ed01fb3f77","DS_eda79f81b5","DS_edc8701de5","DS_edf8e6773c","DS_ee39bb51bf","DS_eee2886ce5","DS_ef6f0a52dc","DS_ef7873e854","DS_f10e32c6b8","DS_f280f1be3e","DS_f2a3eb5df6","DS_f512061797","DS_f6225d4325","DS_f72ff9e9e7","DS_f9dd9912d5","DS_fb16715569","DS_fb0ad203b2","DS_fc8cffedb5","DS_fe928edc5c","DS_fe9f5bc9d1","DS_ffae05ef2c");
  
    public static final List<String> failedQueryPageList = Arrays.asList("https://amoebadb.org/amoeba/app/search/transcript/GenesByNgsSnps");
    public static final List<String> failedFullNameList = Arrays.asList("GeneQuestions.GenesByNgsSnps");
    public static final List<Boolean> failedHasParametersList = Arrays.asList(true,true);
    
}
