// need to edit the final int NUMBER for however many boxes
// comment out the do/while for only solution - no user control
// works for 1-7 (same height for each box)

package GeometricShapes;

/**
 *
 * @author shloak
 */
import java.awt.*;
import java.util.*;
public class Hanoi3
{
    static final int NUMBER = 4; // works for numbers 1 to 7
    static final int barLength = 500;
    static final int barWidth = 50;
    static final int spaceBetween = 200;
    static final int boxHeight = 70;
    static final int large = 220;
    static final int difference = ((large-60)/NUMBER);
    static int counter = 0;
    static final int[][] current = new int[NUMBER][3];
    static final int[][] end = new int[NUMBER][3];
    
    public static void main (String[] args)
    {
        DrawingPanel panel = new DrawingPanel(880, 600);
        Graphics g = panel.getGraphics();
        Scanner scan = new Scanner (System.in);
        initializeArrays();
        g.setColor(Color.WHITE);
        panel.setBackground(Color.red);
        drawBars(g);
        printArray();
        drawBoxes(g, NUMBER);
        /*do
        {
            move(scan, g);
        }
        while (!done());
        System.out.println("You took " + counter + " steps to reach the end!");*/ 
        if (counter == minimumSteps())
        {
            System.out.println("This is the least amount of steps possible!");
            System.out.println("GAME OVER! (close the other window)");
        }
        else
        {
            System.out.println("Go to the other window to see the solution that takes only 7 steps");
            g.setColor(Color.red);
            g.fillRect(0, 0, 880, 600);
            drawBars(g);
            drawBoxes(g, NUMBER);
            initializeArrays();
            solution(0, 2, NUMBER, g);
            timeWaste();
            g.setColor(Color.black);
            g.fillRect(0, 0, 880, 600);
            System.out.println("GAME OVER! (close the other window)");
        } 
    }
    public static void drawBars (Graphics g)
    {
        g.setColor(Color.white);
        for (int i = 0; i < 3; i++)
        {
            g.drawRect(100+300*i, 50, barWidth, 500);
        }
    }
    public static void drawBoxes (Graphics g, int number)
    {
        g.setColor(Color.white);
        for (int i = 0; i < number; i++)
        {
            g.drawRect(100-(large-difference*i-barWidth)/2, 550-(boxHeight*(i+1)), large-difference*i, boxHeight);
        }
    }
    public static void initializeArrays ()
    {
       for (int i = 0; i < NUMBER; i++)
       {
         for (int k = 0; k < 3; k++)
         {
            if (k == 0)
               current[i][k] = (i+1);
            else
               current[i][k] = 0;
         }
       }
       for (int i = 0; i < NUMBER; i++)
       {
         for (int k = 0; k < 3; k++)
         {
            if (k == 2)
               end[i][k] = (i+1);
            else
               end[i][k] = 0;
         }
       }
    }
    public static boolean done ()
    {
        return Arrays.deepEquals(current, end);
    }
    public static int findLowest (int a)
    {
      for (int i = 1; i <= NUMBER; i++)
      {
         if (current[NUMBER-i][a] == 0)
            return (NUMBER-i);
      }
      return -1;
   }
   public static int findHighest (int a)
   {
      for (int i = 0; i < NUMBER; i++)
      {
         if (current[i][a] != 0)
            return i;
      }
      return (NUMBER-1);
   }
 
    public static void move (Scanner a, Graphics g)
    {
        System.out.print("Type in where you want to go from, x, and where you want to, y, go to in the form x (space) y: ");
        int go = a.nextInt() - 1;
        int to = a.nextInt() - 1;
        if (canGo(go, to))
        {
            counter++;
            deleteSquare(go, g);
            drawBars(g);
            moveSquares(go, to, g);
            int temp = current[findHighest(go)][go];
            current[findHighest(go)][go] = 0;
            current[findLowest(to)][(to)] = temp;
            printArray();
        }
        else
            System.out.println("Not a valid move - try again. ");
    }
    public static void deleteSquare (int go, Graphics g)
    {
        g.setColor(Color.red);
        int mover = current[findHighest(go)][go];
        int dim = dimensions(mover);
        int extra = 0;
        for (int i = (NUMBER-1); i > findHighest(go); i--)
        {
            int value = current[i][go];
            if (value != 0)
                extra += boxHeight;
        }
        g.drawRect(100+300*(go)-(dim-barWidth)/2, 550-boxHeight-extra, dim, boxHeight);   
        g.setColor(Color.white);
        if (findHighest(go) != (NUMBER-1))
        {
            int dim2 = dimensions(current[findHighest(go)+1][go]);
            g.drawRect(100+300*go-(dim2-barWidth)/2, 550-extra, dim2, boxHeight);
        }
    }
    public static void moveSquares (int go, int to, Graphics g)
    {
        g.setColor(Color.white);
        int mover = current[findHighest(go)][go];
        int dim = dimensions(mover);
        int extra = getExtra(to);
        g.drawRect(100+300*(to)-(dim-barWidth)/2, 550-boxHeight-extra, dim, boxHeight);
    }
    public static int getExtra (int val)
    {
        int sum = 0;
        for (int i = 0; i < (NUMBER-1); i++)
        {
            int value = current[(NUMBER-1) - i][val];
            if (value != 0)
                sum += boxHeight;
        }
        return sum;
    }
    public static int dimensions (int mover)
    {
        return large - difference*(NUMBER-mover);
    }
    public static boolean canGo (int a, int b)
    {
        return (current[findHighest(b)][b] > current[findHighest(a)][a]) || (current[findHighest(b)][b] == 0);
    }
    public static void printArray()
    {
      for (int i = 0; i < NUMBER; i++)
      {
         for (int k = 0; k < 3; k++)
         {
            System.out.print(current[i][k] + " ");
         }
         System.out.println();
      }  
    }
    public static void solution (int go, int to, int num, Graphics g)
    {
        if (num == 1)
        {
            timeWaste();
            deleteSquare(go, g);
            drawBars(g);
            moveSquares(go, to, g);
            timeWaste();
            int temp = current[findHighest(go)][go];
            current[findHighest(go)][go] = 0;
            current[findLowest(to)][(to)] = temp;
        }
        else
        {
            int helper = 3-go-to;
            solution (go, (helper), (num-1), g);
            timeWaste();
            deleteSquare(go, g);
            drawBars(g);
            moveSquares(go, to, g);
            timeWaste();
            int temp = current[findHighest(go)][go];
            current[findHighest(go)][go] = 0;
            current[findLowest(to)][(to)] = temp;
            solution((helper), to, (num-1), g);
        }
    }
    public static int minimumSteps ()
    {

        return (int)Math.pow(2, NUMBER) - 1;
    }
    public static void timeWaste ()
    {
        for (int i = 1; i < 150000; i++)
        {
            double a = Math.pow(Math.PI, i);
            double b = Math.pow(Math.PI, a);
            double c = Math.pow(Math.PI, b);
        }
    }
}