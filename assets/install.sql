CREATE TABLE alphabet(
  alphabetid    INTEGER PRIMARY KEY, 
  alphabetlettre  TEXT
);
CREATE TABLE mot(
  motid     INTEGER, 
  motlettre   TEXT, 
  motalphabet INTEGER,
  FOREIGN KEY(motalphabet) REFERENCES alphabet(alphabetid)
);