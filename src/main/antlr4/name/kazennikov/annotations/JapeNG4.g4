grammar JapeNG4;

@header {
  package name.kazennikov.annotations;
}


jape: multiPhaseHead? (singlePhase | phasesDecl);

ident: SIMPLE | 'f' | 'F' | 'd' | 'D' | 'e' | 'E';

// multi phase grammar
multiPhaseHead: ('MultiPhase:' | 'Multiphase:') ident;

phasesDecl: 'Phases:' SIMPLE+;

imports: 'Imports:' javaCode;
controllerOpt: controllerStarted | controllerFinished | controllerAborted;

controllerStarted: 'ControllerStarted:' javaCode;
controllerFinished: 'ControllerFinished:' javaCode;
controllerAborted: 'ControllerAborted:' javaCode;

// single phase grammar
preHead: imports? (controllerOpt)*;
singlePhase: preHead phase+;
phase: 'Phase:' ident (input | opts)* (singleRule | macro | templateDef)+;
input: 'Input:' ident+;
opts: ('Options:' | 'options:') option (','? option)*;
option: ident '=' ident;
macro: lhsMacro | rhsMacro;
lhsMacro: ('Macro:' | 'MACRO:') ident (matcher | simpleMatcher);
rhsMacro: ('Macro:' | 'MACRO:') ident action;

templateDef: 'Template:' ident '=' value;
templateRef: '[' ident templateParams? ']';
templateParams:(templateKV (',' templateKV)*);
templateKV: ident '=' ident;

singleRule: 'Rule:' ident priority? ruleElem ('|' ruleElem)* '-->' actions;
ruleElem: (matcher | simpleMatcher | ident)+;
priority: 'Priority:' DIGITS;
actions: action (',' action)*;

// LHS grammar
matcher: group modif? label?;
group: '(' groupAnd ('|' groupAnd)*')';

groupAnd: groupElem+;
groupElem: matcher | simpleMatcher | ident;

simpleMatcher: '{' annotSpec (',' annotSpec)* '}';
annotSpec: '!' simpleAnnotSpec
         | simpleAnnotSpec
         ;

simpleAnnotSpec: ident
               | ident '.' ident op value
               | ident '@' ident op value
               | ident ident (simpleMatcher | ident)
               ;

attrName: ident
        | STRING
        ;

value: ident
     | STRING
     | floatingPoint
     ;

op: '!='
  | '=='
  | '=~'
  | '==~'
  | '>'
  | '>='
  | '<'
  | '<='
  | '!=~'
  | '!~'
  | ident
  ;




modif: '?'
     | '*'
     | '+'
     | '[' DIGITS (',' to=DIGITS?)? ']'
     ;

label: (':' ident);


// RHS grammar
action: labeling | label? javaCode | rhsMacroRef;
javaCode: '{' (
        ident | DIGITS | STRING | op |'(' | ')' | ',' | '.' | '<' | '>' | '[' | ']' | ':' | '=' | '!=' | '+' | '-' |'!' | '|' | '~' |
        'e' | 'E' | 'f' | 'F' | 'd' | 'D' | '+' | '-' | '?' | '*' | ';' | '&' | '-' | '/' | '\'' |
        javaCode)* '}'
        ;

rhsMacroRef: ident;

labeling: ':' ident '.' ident '=' '{' (attr (',' attr )*)?'}';

attr:  attrName '=' attrValue;
attrValue: value
         |':' ident '.' ident '.' ident
         |':' ident '.' ident '@' ident
         ;



WS: (' ' | '\t' | '\n' | '\r')+ -> skip;
SINGLE_COMMENT: '//' ~('\r' | '\n')* -> skip;
COMMENT:   '/*' (.)*? '*/' -> skip;


floatingPoint: ('-'|'+')?
             (
               DIGITS
             | DIGITS '.' DIGITS exponent? ('f' | 'F' | 'd' | 'D')?
             | '.' DIGITS exponent? ('f' | 'F' | 'd' | 'D')?
             | DIGITS exponent  ('f' | 'F' | 'd' | 'D')?
             | DIGITS exponent? ('f' | 'F' | 'd' | 'D')
             );

exponent: ('e' | 'E') ('-'|'+')? DIGITS;

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
//SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':' | '=' | '!' | '~' | '"' | '@')+;

SIMPLE: JavaLetter JavaLetterOrDigit*
;



fragment
JavaLetter
: [a-zA-Z$_] // these are the "java letters" below 0xFF
| // covers all characters above 0xFF which are not a surrogate
~[\u0000-\u00FF\uD800-\uDBFF]
{Character.isJavaIdentifierStart(_input.LA(-1))}?
| // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
[\uD800-\uDBFF] [\uDC00-\uDFFF]
{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
;

fragment
JavaLetterOrDigit
: [a-zA-Z0-9$_] // these are the "java letters or digits" below 0xFF
| '-'
| // covers all characters above 0xFF which are not a surrogate
~[\u0000-\u00FF\uD800-\uDBFF]
{Character.isJavaIdentifierPart(_input.LA(-1))}?
| // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
[\uD800-\uDBFF] [\uDC00-\uDFFF]
{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
;
