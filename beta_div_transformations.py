import pandas as pd
import numpy as np
from IPython.display import display
import os

#Take in the file and read it in as a dataframe
file_path = input('Enter the condensed microarray spreadsheet path: ')
if '"' in file_path or "'" in file_path:
    file_path = file_path.replace('"', '')
    file_path = file_path.replace("'", "")
input_df = pd.read_csv(file_path, dtype=str)

#Adjust the columns/rows accordingly
df = input_df.iloc[:, 3:].copy()
df = df.iloc[1:, :]
df.set_index(df.iloc[:, 0], inplace=True)
df.drop(df.columns[0], axis=1, inplace=True)

#Convert data to binary format
df.replace(['DETECTED', 'Secondary'], 1, inplace=True)
df.fillna(0, inplace=True)

#View what we have
display(df)

#Download the new df as a CSV to the same folder as the uploaded one
output_dir = os.path.dirname(file_path) + '\\beta_div_transformed_axiom.csv'
df.to_csv(output_dir)

print(f"File has been downloaded to {output_dir}")