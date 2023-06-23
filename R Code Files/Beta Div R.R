library(vegan)
data_table2 <- read.table("clipboard", header = TRUE)
rownames(data_table2) <- data_table2$Family
data_table2$Family <- NULL
data_table2 <- t(data_table2)
group <- factor(row.names(data_table2))
dist <- vegdist(data_table2, method = "bray")
betadiv <- betadisper(dist, group) 
