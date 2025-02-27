package jp487bluebook.app.utilities;

import java.util.Arrays;
/* 
 
This algorithm is from:

How to Calculate Levenshtein Distance in Java?
by Deep Jain

Source: https://www.baeldung.com/java-levenshtein-distance 
  
 */
public class Levenshtein {
    public static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];
     
        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] 
                     + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), 
                      dp[i - 1][j] + 1, 
                      dp[i][j - 1] + 1);
                }
            }
        }
     
        return dp[x.length()][y.length()];
    }
    
    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
    
    public static int min(int... numbers) {
        return Arrays.stream(numbers)
          .min().orElse(Integer.MAX_VALUE);
    }
}
