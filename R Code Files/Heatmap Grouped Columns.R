install.packages("pheatmap")
library(pheatmap)
data = read.csv("C:\\Users\\kirti.p\\Desktop\\Microbiome\\HeatMap CSV.csv")
rownames(data) <- data$Family
data$Family <- NULL
pheatmap(data, cluster_cols = F, main = "pheatmap default")
