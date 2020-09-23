package lexer;

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

}
