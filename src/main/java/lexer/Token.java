package lexer;

import common.AlphabetHelper;
import common.PeekIterator;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhangran
 * @since 2020-09-23
 **/
@Data
@ToString
public class Token {
    private TokenType type;
    private String value;

    public Token() {
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }

    public boolean isScalar() {
        return type == TokenType.INTEGER || type == TokenType.BOOLEAN || type == TokenType.FLOAT || type == TokenType.STRING;
    }

    /**
     * 提取变量或关键字
     *
     * @param it
     * @return
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {
        String s = "";
        while (it.hasNext()) {
            char c = it.peek();
            if (AlphabetHelper.isLiteral(c)) {
                s += c;
            } else {
                break;
            }
            it.next();
        }
        if (Keywords.isKeyword(s)) {
            return new Token(TokenType.KEYWORD, s);
        }

        if ("true".equals(s) || "false".equals(s)) {
            return new Token(TokenType.BOOLEAN, s);
        }

        return new Token(TokenType.VARIABLE, s);
    }

    /**
     * 提取字符串
     *
     * @param it
     * @return
     */
    public static Token makeString(PeekIterator<Character> it) throws LexicalException {
        String s = "";
        int state = 0;
        while (it.hasNext()) {
            char c = it.next();
            switch (state) {
                case 0:
                    if (c == '\"') {
                        state = 1;
                    } else {
                        state = 2;
                    }
                    s += c;
                    break;
                case 1:
                    if (c == '"') {
                        return new Token(TokenType.STRING, s + c);
                    } else {
                        s += c;
                    }
                    break;

                case 2:
                    if (c == '\'') {
                        return new Token(TokenType.STRING, s + c);
                    } else {
                        s += c;
                    }
                    break;

            }
        }
        throw new LexicalException("Unexpected error");
    }

}
