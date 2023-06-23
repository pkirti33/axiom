install.packages("pheatmap")
library(pheatmap)
data = read.csv("C:\\Users\\kirti.p\\Desktop\\Microbiome\\HeatMap Ungrouped CSV.csv")
rownames(data) <- data$Family
data$Family <- NULL
col_annot = read.csv("C:\\Users\\kirti.p\\Desktop\\Microbiome\\HeatMap Ungrouped Annotations.csv", header = FALSE)
rownames(col_annot) <- c("ID", "Group")
annotation_colors=list(
  Group = c(NP="mediumpurple", DNP="maroon", DP="palegreen", UCV="skyblue"))

pheatmap(data, 
         main = "Heatmap",
         color= colorRampPalette(c("orchid","lavender","steelblue2"))(3),
         annotation_col = col_annot,
         clustering_method = "complete", 
         annotation_colors = annotation_colors
         )
         