import time
import csv
abbreviations = []
tweets3175=[]
tweets6250=[]
tweets12500=[]
tweets25000=[]
tweets50000=[]
tweets100000=[]
tweets200000=[]
tweets400000=[]
tweets800000=[]
tweets1600000=[]
tweetnums=[(3175, tweets3175),(6250, tweets6250),(12500, tweets12500),(25000, tweets25000),(50000, tweets50000),(100000, tweets100000),(200000, tweets200000),(400000, tweets400000),(800000, tweets800000),(1600000, tweets1600000)]

with open('clean_AcronymsFile-milestone4.csv', 'r') as file:
    reader = csv.reader(file)
    for row in reader:
        tuple=(row[0],row[1])
        abbreviations.append(tuple)
        
for maxTweet in tweetnums:        
    with open('upDatedTweetData-milestone4.csv', 'r') as file:
        reader = csv.reader(file)
        i=0
        for row in reader:
            i+=1
            if i <= maxTweet[0]:
                maxTweet[1].append(row[1])
            

class TrieNode:
    def __init__(self):
        self.children = {}  
        self.isEndofWord = False 
        self.fullForm = None

def insertWord(root, word, fullForm):
    crnt = root
    for c in word:
        if c not in crnt.children:
            crnt.children[c] = TrieNode()
        crnt = crnt.children[c]  
    crnt.isEndofWord = True  
    crnt.fullForm = fullForm  


def searchWord(root, word):
    crnt = root
    for c in word:
        if c not in crnt.children:
            return None
        crnt = crnt.children[c]
    if crnt.isEndofWord:
        return crnt.fullForm 
    return None


def replaceAbbreviations(tweet, abbreviations, root):
    sb = []
    words = tweet.split()
    for word in words:
        fullForm = searchWord(root, word)
        if fullForm is not None:
            sb.append(fullForm)
        else:
            sb.append(word)
        sb.append(" ")
    return "".join(sb).strip()


def modifyTweets(tweets, abbreviations):
    root = TrieNode()
    for abbreviation in abbreviations:
        insertWord(root, abbreviation[0], abbreviation[1])
    start_time = time.time()
    modified_count = 0
    for i in range(len(tweets)):
        tweets[i] = replaceAbbreviations(tweets[i], abbreviations, root)
        modified_count += 1
    elapsed_time = round((time.time() - start_time) * 1000)
    print(f"{modified_count} tweets. Elapsed Time: {elapsed_time} ms")
    
#tweets = ["That girl is a SOB.", "I like icecream but OTOH chocolate is really good too so choosing is a difficult choice."]
# ^^ the list of tweet are stored in a list this this. ^^
# abbreviations = [("SOB", "son of a bitch"), ("OTOH", "on the other hand")]
# ^^ The list of abbriviations are stored in a list like this. ^^
  
for tweet in tweetnums:       
    modifyTweets(tweet[1], abbreviations)