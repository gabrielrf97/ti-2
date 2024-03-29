import numpy as np
import pandas as pd
import re
import shutil
import string
import seaborn as sns
import matplotlib.pyplot as plt

from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.pipeline import make_pipeline
from sklearn.metrics import confusion_matrix, accuracy_score
import joblib

df = pd.read_csv('dataset.csv')
y_train=df['platform']
x_train = df.drop('platform', axis=1)

train, test = np.split(df.sample(frac=1), [int(0.85 * len(df))])
len(train)

model = make_pipeline(TfidfVectorizer(), MultinomialNB())
model.fit(train['question'], train['platform'])
predicted_categories = model.predict(test['question'])

mat = confusion_matrix(test['platform'], predicted_categories)
sns.heatmap(mat.T, square = True, annot=True, fmt = "d", xticklabels=['text','image'],yticklabels=['text','image'])
plt.xlabel("true labels")
plt.ylabel("predicted label")
plt.show()
print("The accuracy is {}".format(accuracy_score(test['platform'], predicted_categories)))

def new_prediction(my_sentence, model):
    prediction = model.predict([my_sentence])
    print(prediction)

new_prediction("Gostaria de saber a altura do neymar", model)
new_prediction("Altura do neymar", model)
new_prediction("Crie um desenho do machu picchu", model)
new_prediction("Desenhe a capa de um manga do Elvis presley", model)

joblib.dump(model, 'powerchat-gpt.joblib')
