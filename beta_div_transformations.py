import pandas as pd
import numpy as np
from IPython.display import display
import os

#Define function to remove unecessary rows from a dataframe
def remove_empty_rows(df: pd.DataFrame) -> pd.DataFrame:
    n = len(df.index)
    for row in df.index:
        if pd.isna(row):
            n -= 1
    
    return df.iloc[:n,:]

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

#Convert data to binary format and remove unecessary rows from the bottom
df.replace(['DETECTED', 'Secondary'], 1, inplace=True)
df.fillna(0, inplace=True)
print(df.index)
df = remove_empty_rows(df)

#View what we have
display(df)

#Download the new df as a CSV to the same folder as the uploaded one
output_name = input('Enter the desired output file name. Include .csv at the end. ')
output_dir = os.path.dirname(file_path)  + '\\' + output_name
print(output_dir)
df.to_csv(output_dir)

print(f"File has been downloaded to {output_dir}")

