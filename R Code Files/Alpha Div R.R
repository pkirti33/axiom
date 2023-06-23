library(vegan)
data_table <- read.table("clipboard", header = TRUE)
rownames(data_table) <- data_table$Family
data_table$Family <- NULL
data_table <- t(data_table)
a_diversity <- diversity(data_table, index = "shannon")
