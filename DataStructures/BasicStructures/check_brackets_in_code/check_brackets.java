import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        int failedPosition  = -1;
        for (int position = 0; position < text.length();position++) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
                Bracket openingBracket = new Bracket(next,position);
                opening_brackets_stack.push(openingBracket);
            }

            if (next == ')' || next == ']' || next == '}') {
                if(!opening_brackets_stack.isEmpty()){
                    Bracket brOpened = opening_brackets_stack.pop();
                    if(!brOpened.Match(next)){
                        failedPosition = position + 1;
                        break;
                    }
                }
                else{
                    failedPosition = position+1;
                    break;
                }
            }
        }

        if(!opening_brackets_stack.isEmpty() && failedPosition==-1){
            failedPosition  = opening_brackets_stack.get(0).position+1;
        }


        if(failedPosition!=-1){
            System.out.println(failedPosition);

        }
        else{
            System.out.println("Success");
        }
        // Printing answer, write your code here
    }
}
