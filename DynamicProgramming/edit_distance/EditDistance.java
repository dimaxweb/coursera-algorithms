import java.util.*;

class EditDistance {



    public static  int EditDistanceTopBottom(String word1,String word2)
    {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }


    public static int EditDistance(String word1, String word2) {
    if (word1.isEmpty()) return word2.length();
    if (word2.isEmpty()) return word1.length();

    int word1Length = word1.length();
    int word2Length = word2.length();

    //minCosts[i][j] represents the edit distance of the substrings
    //word1.substring(i) and word2.substring(j)
    int[][] minCosts = new int[word1Length][word2Length];

    //This is the edit distance of the last char of word1 and the last char of word2
    //It can be 0 or 1 depending on whether the two are different or equal
    minCosts[word1Length - 1][word2Length - 1] = replaceCost(word1, word2, word1Length - 1, word2Length - 1);

    for (int j = word2Length - 2; j >= 0; j--) {
      minCosts[word1Length - 1][j] = 1 + minCosts[word1Length - 1][j + 1];
    }

    for (int i = word1Length - 2; i >= 0; i--) {
      minCosts[i][word2Length - 1] = 1 + minCosts[i + 1][word2Length - 1];
    }



    for (int i = word1Length - 2; i >= 0; i--) {
      for (int j = word2Length - 2; j >= 0; j--) {
        int replace = replaceCost(word1, word2, i, j) + minCosts[i + 1][j + 1];
        int delete = 1 + minCosts[i + 1][j];
        int insert = 1 + minCosts[i][j + 1];
        minCosts[i][j] = min(replace, delete, insert);
      }
    }
    return minCosts[0][0];


  }

    public static int replaceCost(String w1, String w2, int w1Index, int w2Index) {
        return (w1.charAt(w1Index) == w2.charAt(w2Index)) ? 0 : 1;
    }

  public static int min(int... numbers) {
    int result = Integer.MAX_VALUE;
    for (int each : numbers) {
      result = Math.min(result, each);
    }
    return result;
  }

  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(EditDistanceTopBottom(s, t));
  }

}
