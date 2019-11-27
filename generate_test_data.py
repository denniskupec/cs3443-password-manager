import sqlite3, requests
from faker import Faker
from PIL import Image
from io import BytesIO

conn = sqlite3.connect('storage.sqlite3')
c = conn.cursor()
f = Faker()

n = 100
favicon = Image.open(BytesIO(requests.get("https://placekitten.com/50/50").content))

entries = [ [f.binary(8), f.iso8601(), f.text(max_nb_chars=50, ext_word_list=None), f.user_name(), f.binary(f.random_int(min=6, max=26, step=1)), f.uri(), favicon, f.paragraph(nb_sentences=3, variable_nb_sentences=True, ext_word_list=None)] for _ in range(n) ]

c.execute("INSERT INTO entries (salt, updated_at, title, username, password, url, favicon, note) VALUES (?,?,?,?,?,?,?,?)", entries)
