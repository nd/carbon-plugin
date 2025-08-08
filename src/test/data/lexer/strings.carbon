""
"ok"
"\""
"\n"
"\\"
"\\\""
"\z"

"
"

// tab:
"	"

#""#
#"ok"#
#"  \#"  "#

##"Raw strings #"nesting"#"##
#"Tab is expressed as \t. Example: '\#t'"#


'''
'''

'''cpp
int main() {
}
'''

// two invalid block strings:
'
''


'''
escape: \'''
'''

#'''
  ''' <-- This is not the end of the string.
  '''#

// two invalid raw block strings:
#'
#''