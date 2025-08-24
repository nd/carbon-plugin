package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class CbToken(val name: String, val id: Int) : IElementType(name, CbLanguage) {

  companion object {
    @JvmField val WHITESPACE = CbToken("whitespace", CbTokenId.WHITESPACE)
    @JvmField val NEWLINE = CbToken("newline", CbTokenId.NEWLINE)
    @JvmField val COMMENT = CbToken("comment", CbTokenId.COMMENT)
    @JvmField val NUMBER = CbToken("number", CbTokenId.NUMBER)

    @JvmField val GREATER_GREATER_EQ = CbToken("GREATER_GREATER_EQ", CbTokenId.GREATER_GREATER_EQ)
    @JvmField val LESS_LESS_EQ = CbToken("LESS_LESS_EQ", CbTokenId.LESS_LESS_EQ)
    @JvmField val AMP_EQ = CbToken("AMP_EQ", CbTokenId.AMP_EQ)
    @JvmField val CARET_EQ = CbToken("CARET_EQ", CbTokenId.CARET_EQ)
    @JvmField val COLON_EQ = CbToken("COLON_EQ", CbTokenId.COLON_EQ)
    @JvmField val COLON_EXCL = CbToken("COLON_EXCL", CbTokenId.COLON_EXCL)
    @JvmField val EQ_EQ = CbToken("EQ_EQ", CbTokenId.EQ_EQ)
    @JvmField val EQ_GREATER = CbToken("EQ_GREATER", CbTokenId.EQ_GREATER)
    @JvmField val EXCL_EQ = CbToken("EXCL_EQ", CbTokenId.EXCL_EQ)
    @JvmField val GREATER_EQ = CbToken("GREATER_EQ", CbTokenId.GREATER_EQ)
    @JvmField val GREATER_GREATER = CbToken("GREATER_GREATER", CbTokenId.GREATER_GREATER)
    @JvmField val LESS_EQ = CbToken("LESS_EQ", CbTokenId.LESS_EQ)
    @JvmField val LESS_GREATER = CbToken("LESS_GREATER", CbTokenId.LESS_GREATER)
    @JvmField val LESS_LESS = CbToken("LESS_LESS", CbTokenId.LESS_LESS)
    @JvmField val LESS_MINUS = CbToken("LESS_MINUS", CbTokenId.LESS_MINUS)
    @JvmField val MINUS_EQ = CbToken("MINUS_EQ", CbTokenId.MINUS_EQ)
    @JvmField val MINUS_GREATER = CbToken("MINUS_GREATER", CbTokenId.MINUS_GREATER)
    @JvmField val MINUS_MINUS = CbToken("MINUS_MINUS", CbTokenId.MINUS_MINUS)
    @JvmField val PERCENT_EQ = CbToken("PERCENT_EQ", CbTokenId.PERCENT_EQ)
    @JvmField val PIPE_EQ = CbToken("PIPE_EQ", CbTokenId.PIPE_EQ)
    @JvmField val PLUS_EQ = CbToken("PLUS_EQ", CbTokenId.PLUS_EQ)
    @JvmField val PLUS_PLUS = CbToken("PLUS_PLUS", CbTokenId.PLUS_PLUS)
    @JvmField val SLASH_EQ = CbToken("SLASH_EQ", CbTokenId.SLASH_EQ)
    @JvmField val STAR_EQ = CbToken("STAR_EQ", CbTokenId.STAR_EQ)
    @JvmField val TILDE_EQ = CbToken("TILDE_EQ", CbTokenId.TILDE_EQ)
    @JvmField val AMP = CbToken("AMP", CbTokenId.AMP)
    @JvmField val CARET = CbToken("CARET", CbTokenId.CARET)
    @JvmField val COLON = CbToken("COLON", CbTokenId.COLON)
    @JvmField val EQ = CbToken("EQ", CbTokenId.EQ)
    @JvmField val EXCL = CbToken("EXCL", CbTokenId.EXCL)
    @JvmField val GREATER = CbToken("GREATER", CbTokenId.GREATER)
    @JvmField val LESS = CbToken("LESS", CbTokenId.LESS)
    @JvmField val MINUS = CbToken("MINUS", CbTokenId.MINUS)
    @JvmField val PERCENT = CbToken("PERCENT", CbTokenId.PERCENT)
    @JvmField val PERIOD = CbToken("PERIOD", CbTokenId.PERIOD)
    @JvmField val PIPE = CbToken("PIPE", CbTokenId.PIPE)
    @JvmField val PLUS = CbToken("PLUS", CbTokenId.PLUS)
    @JvmField val QUESTION = CbToken("QUESTION", CbTokenId.QUESTION)
    @JvmField val SLASH = CbToken("SLASH", CbTokenId.SLASH)
    @JvmField val STAR = CbToken("STAR", CbTokenId.STAR)
    @JvmField val TILDE = CbToken("TILDE", CbTokenId.TILDE)
    @JvmField val COMMA = CbToken("COMMA", CbTokenId.COMMA)
    @JvmField val SEMI = CbToken("SEMI", CbTokenId.SEMI)
    @JvmField val OPEN_BRACKET = CbToken("OPEN_BRACKET", CbTokenId.OPEN_BRACKET)
    @JvmField val CLOSE_BRACKET = CbToken("CLOSE_BRACKET", CbTokenId.CLOSE_BRACKET)
    @JvmField val OPEN_PAREN = CbToken("OPEN_PAREN", CbTokenId.OPEN_PAREN)
    @JvmField val CLOSE_PAREN = CbToken("CLOSE_PAREN", CbTokenId.CLOSE_PAREN)
    @JvmField val OPEN_BRACE = CbToken("OPEN_BRACE", CbTokenId.OPEN_BRACE)
    @JvmField val CLOSE_BRACE = CbToken("CLOSE_BRACE", CbTokenId.CLOSE_BRACE)

    @JvmField val IDENTIFIER = CbToken("IDENTIFIER", CbTokenId.IDENTIFIER)
    @JvmField val RAW_IDENTIFIER = CbToken("RAW_IDENTIFIER", CbTokenId.RAW_IDENTIFIER)

    @JvmField val ABSTRACT = CbToken("abstract", CbTokenId.ABSTRACT)
    @JvmField val ADAPT = CbToken("adapt", CbTokenId.ADAPT)
    @JvmField val ADDR = CbToken("addr", CbTokenId.ADDR)
    @JvmField val ALIAS = CbToken("alias", CbTokenId.ALIAS)
    @JvmField val AND = CbToken("and", CbTokenId.AND)
    @JvmField val AS = CbToken("as", CbTokenId.AS)
    @JvmField val AUTO = CbToken("auto", CbTokenId.AUTO)
    @JvmField val BASE = CbToken("base", CbTokenId.BASE)
    @JvmField val BREAK = CbToken("break", CbTokenId.BREAK)
    @JvmField val CORE = CbToken("Core", CbTokenId.CORE)
    @JvmField val CASE = CbToken("case", CbTokenId.CASE)
    @JvmField val CHOICE = CbToken("choice", CbTokenId.CHOICE)
    @JvmField val CLASS = CbToken("class", CbTokenId.CLASS)
    @JvmField val CONSTRAINT = CbToken("constraint", CbTokenId.CONSTRAINT)
    @JvmField val CONTINUE = CbToken("continue", CbTokenId.CONTINUE)
    @JvmField val DEFAULT = CbToken("default", CbTokenId.DEFAULT)
    @JvmField val DESTROY = CbToken("destroy", CbTokenId.DESTROY)
    @JvmField val ELSE = CbToken("else", CbTokenId.ELSE)
    @JvmField val EXPORT = CbToken("export", CbTokenId.EXPORT)
    @JvmField val EXTEND = CbToken("extend", CbTokenId.EXTEND)
    @JvmField val EXTERN = CbToken("extern", CbTokenId.EXTERN)
    @JvmField val FINAL = CbToken("final", CbTokenId.FINAL)
    @JvmField val FN = CbToken("fn", CbTokenId.FN)
    @JvmField val FOR = CbToken("for", CbTokenId.FOR)
    @JvmField val FORALL = CbToken("forall", CbTokenId.FORALL)
    @JvmField val FRIEND = CbToken("friend", CbTokenId.FRIEND)
    @JvmField val IF = CbToken("if", CbTokenId.IF)
    @JvmField val IMPL = CbToken("impl", CbTokenId.IMPL)
    @JvmField val IMPLS = CbToken("impls", CbTokenId.IMPLS)
    @JvmField val IMPORT = CbToken("import", CbTokenId.IMPORT)
    @JvmField val IN = CbToken("in", CbTokenId.IN)
    @JvmField val INTERFACE = CbToken("interface", CbTokenId.INTERFACE)
    @JvmField val LET = CbToken("let", CbTokenId.LET)
    @JvmField val LIBRARY = CbToken("library", CbTokenId.LIBRARY)
    @JvmField val LIKE = CbToken("like", CbTokenId.LIKE)
    @JvmField val MATCH = CbToken("match", CbTokenId.MATCH)
    @JvmField val NAMESPACE = CbToken("namespace", CbTokenId.NAMESPACE)
    @JvmField val NOT = CbToken("not", CbTokenId.NOT)
    @JvmField val OBSERVE = CbToken("observe", CbTokenId.OBSERVE)
    @JvmField val OR = CbToken("or", CbTokenId.OR)
    @JvmField val OVERRIDE = CbToken("override", CbTokenId.OVERRIDE)
    @JvmField val PACKAGE = CbToken("package", CbTokenId.PACKAGE)
    @JvmField val PARTIAL = CbToken("partial", CbTokenId.PARTIAL)
    @JvmField val PRIVATE = CbToken("private", CbTokenId.PRIVATE)
    @JvmField val PROTECTED = CbToken("protected", CbTokenId.PROTECTED)
    @JvmField val REQUIRE = CbToken("require", CbTokenId.REQUIRE)
    @JvmField val RETURN = CbToken("return", CbTokenId.RETURN)
    @JvmField val RETURNED = CbToken("returned", CbTokenId.RETURNED)
    @JvmField val SELF = CbToken("self", CbTokenId.SELF)
    @JvmField val SELF_TYPE = CbToken("Self", CbTokenId.SELF_TYPE)
    @JvmField val TEMPLATE = CbToken("template", CbTokenId.TEMPLATE)
    @JvmField val THEN = CbToken("then", CbTokenId.THEN)
    @JvmField val TYPE = CbToken("type", CbTokenId.TYPE)
    @JvmField val VAR = CbToken("var", CbTokenId.VAR)
    @JvmField val VIRTUAL = CbToken("virtual", CbTokenId.VIRTUAL)
    @JvmField val WHERE = CbToken("where", CbTokenId.WHERE)
    @JvmField val WHILE = CbToken("while", CbTokenId.WHILE)

    @JvmField val NUMERIC_TYPE_LITERAL = CbToken("numeric_type_literal", CbTokenId.NUMERIC_TYPE_LITERAL)
    @JvmField val STRING = CbToken("STRING", CbTokenId.STRING)
    @JvmField val RAW_STRING = CbToken("RAW_STRING", CbTokenId.RAW_STRING)
    @JvmField val BLOCK_STRING = CbToken("BLOCK_STRING", CbTokenId.BLOCK_STRING)
    @JvmField val RAW_BLOCK_STRING = CbToken("RAW_BLOCK_STRING", CbTokenId.RAW_BLOCK_STRING)

    @JvmField
    val KEYWORDS = arrayOf<CbToken>(
      ABSTRACT, ADAPT, ADDR, ALIAS, AND, AS, AUTO, BASE, BREAK, CORE, CASE, CHOICE, CLASS, CONSTRAINT, CONTINUE, DEFAULT,
      DESTROY, ELSE, EXPORT, EXTEND, EXTERN, FINAL, FN, FOR, FORALL, FRIEND, IF, IMPL, IMPLS, IMPORT, IN, INTERFACE, LET, LIBRARY,
      LIKE, MATCH, NAMESPACE, NOT, OBSERVE, OR, OVERRIDE, PACKAGE, PARTIAL, PRIVATE, PROTECTED, REQUIRE, RETURN, RETURNED,
      SELF, SELF_TYPE, TEMPLATE, THEN, TYPE, VAR, VIRTUAL, WHERE, WHILE,
    )

    @JvmField
    val OPERATORS = arrayOf<CbToken>(
      GREATER_GREATER_EQ, LESS_LESS_EQ, AMP_EQ, CARET_EQ, COLON_EQ, COLON_EXCL, EQ_EQ, EQ_GREATER, EXCL_EQ, GREATER_EQ,
      GREATER_GREATER, LESS_EQ, LESS_GREATER, LESS_LESS, LESS_MINUS, MINUS_EQ, MINUS_GREATER, MINUS_MINUS, PERCENT_EQ,
      PIPE_EQ, PLUS_EQ, PLUS_PLUS, SLASH_EQ, STAR_EQ, TILDE_EQ, AMP, CARET, COLON, EQ, EXCL, GREATER, LESS, MINUS,
      PERCENT, PIPE, PLUS, QUESTION, SLASH, STAR, TILDE
    )

    val WHITESPACES = TokenSet.create(WHITESPACE, NEWLINE)
    val STRINGS = TokenSet.create(STRING, RAW_STRING, BLOCK_STRING, RAW_BLOCK_STRING)
    val COMMENTS = TokenSet.create(COMMENT)
  }
}


class CbTokenId {
  companion object {
    const val WHITESPACE = 1
    const val NEWLINE = 2
    const val COMMENT = 3
    const val NUMBER = 4
    const val GREATER_GREATER_EQ = 5
    const val LESS_LESS_EQ = 6
    const val AMP_EQ = 7
    const val CARET_EQ = 8
    const val COLON_EQ = 9
    const val COLON_EXCL = 10
    const val EQ_EQ = 11
    const val EQ_GREATER = 12
    const val EXCL_EQ = 13
    const val GREATER_EQ = 14
    const val GREATER_GREATER = 15
    const val LESS_EQ = 16
    const val LESS_GREATER = 17
    const val LESS_LESS = 18
    const val LESS_MINUS = 19
    const val MINUS_EQ = 20
    const val MINUS_GREATER = 21
    const val MINUS_MINUS = 22
    const val PERCENT_EQ = 23
    const val PIPE_EQ = 24
    const val PLUS_EQ = 25
    const val PLUS_PLUS = 26
    const val SLASH_EQ = 27
    const val STAR_EQ = 28
    const val TILDE_EQ = 29
    const val AMP = 30
    const val CARET = 31
    const val COLON = 32
    const val EQ = 33
    const val EXCL = 34
    const val GREATER = 35
    const val LESS = 36
    const val MINUS = 37
    const val PERCENT = 38
    const val PERIOD = 39
    const val PIPE = 40
    const val PLUS = 41
    const val QUESTION = 42
    const val SLASH = 43
    const val STAR = 44
    const val TILDE = 45
    const val COMMA = 46
    const val SEMI = 47
    const val OPEN_BRACKET = 48
    const val CLOSE_BRACKET = 49
    const val OPEN_PAREN = 50
    const val CLOSE_PAREN = 51
    const val OPEN_BRACE = 52
    const val CLOSE_BRACE = 53
    const val IDENTIFIER = 54
    const val RAW_IDENTIFIER = 55
    const val ABSTRACT = 56
    const val ADAPT = 57
    const val ADDR = 58
    const val ALIAS = 59
    const val AND = 60
    const val AS = 61
    const val AUTO = 62
    const val BASE = 63
    const val BREAK = 64
    const val CORE = 65
    const val CASE = 66
    const val CHOICE = 67
    const val CLASS = 68
    const val CONSTRAINT = 69
    const val CONTINUE = 70
    const val DEFAULT = 71
    const val DESTROY = 72
    const val ELSE = 73
    const val EXPORT = 74
    const val EXTEND = 75
    const val EXTERN = 76
    const val FINAL = 77
    const val FN = 78
    const val FOR = 79
    const val FORALL = 80
    const val FRIEND = 81
    const val IF = 82
    const val IMPL = 83
    const val IMPLS = 84
    const val IMPORT = 85
    const val IN = 86
    const val INTERFACE = 87
    const val LET = 88
    const val LIBRARY = 89
    const val LIKE = 90
    const val MATCH = 91
    const val NAMESPACE = 92
    const val NOT = 93
    const val OBSERVE = 94
    const val OR = 95
    const val OVERRIDE = 96
    const val PACKAGE = 97
    const val PARTIAL = 98
    const val PRIVATE = 99
    const val PROTECTED = 100
    const val REQUIRE = 101
    const val RETURN = 102
    const val RETURNED = 103
    const val SELF = 104
    const val SELF_TYPE = 105
    const val TEMPLATE = 106
    const val THEN = 107
    const val TYPE = 108
    const val VAR = 109
    const val VIRTUAL = 110
    const val WHERE = 111
    const val WHILE = 112
    const val NUMERIC_TYPE_LITERAL = 113
    const val STRING = 114
    const val RAW_STRING = 115
    const val BLOCK_STRING = 116
    const val RAW_BLOCK_STRING = 117
  }
}

