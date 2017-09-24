#===============================================
# Copyright (C) 2017 All rights reserved.
#=============================================== 
# Filename:   makedex.sh
# Author:     codesky.club
# Date:       2017-09-24
# Description: 
# 
# Modification: 
# 
#===============================================
#!/bin/bash

javac Hello.java

# make sure dx version >= javac, or something like 'bad class file magic (cafebabe) or version(0034.000)...' will be thrown.
dx --dex --output=classes.dex *.class

