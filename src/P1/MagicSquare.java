import java.io.*;

public class MagicSquare {
    public static void main(String[] args) {




        //输入文件和输出文件在这里填写！！！
        String InputFile = "txt/1.txt";
        String OutputFile = "txt/6.txt";




        if (isLegalMagicSquare(InputFile)) {
            System.out.println("这是Magic Square！");
        }
        generateMagicSquare(197,OutputFile);
    }

    public static boolean isLegalMagicSquare(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            int[][] square = new int[1000][1000]; // 数组大小调整为1000
            int n = 0; // 方阵的维度
            int rowSum = 0;
            int colSum = 0;
            int diag1Sum = 0;
            int diag2Sum = 0;

            // 读取文件中的方阵并计算每行、每列、对角线的和
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                n = numbers.length; // 方阵的维度为每行的数字个数
                for (int j = 0; j < n; j++) {
                    square[i][j] = Integer.parseInt(numbers[j]);
                }
                i++;
            }

            // 检查方阵维度是否大于0
            if (n == 0) {
                System.err.println("文件中没有幻方数据");
                br.close();
                return false;
            }

            // 检查是否为方阵
            if (i != n) {
                System.err.println("文件中的数据不是方阵");
                br.close();
                return false;
            }

            // 计算每行、每列、对角线的和
            for (i = 0; i < n; i++) {
                rowSum = 0;
                colSum = 0;
                for (int j = 0; j < n; j++) {
                    rowSum += square[i][j];
                    colSum += square[j][i];
                }
                diag1Sum += square[i][i];
                diag2Sum += square[i][n - i - 1];

                // 检查每行、每列的和是否相等
                if (rowSum != colSum) {
                    System.out.println("这不是Magic Square！");
                    br.close();
                    return false;
                }
            }

            // 检查每行、每列、对角线的和是否相等
            if (rowSum != diag1Sum || rowSum != diag2Sum) {
                System.out.println("这不是Magic Square！");
                br.close();
                return false;
            }

            br.close();
            return true;
        } catch (IOException e) {
            System.err.println("读取文件错误: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("文件中存在无效数字格式.");
        }
        return false;
    }
    public static boolean generateMagicSquare(int n,String OutputFile) {
        //初始化空数组
        int magic[][] = new int[n][n];
        //起始位置是第一行中间位置
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            //每走n步就往下走一格（因为此时右上角已经有数字了）
            if (i % n == 0)
                row++;
            else {
                //如果走到最上行就变成最下行
                if (row == 0)
                    row = n - 1;
                //否则往上一列
                else
                    row--;
                //如果走到最右列就变成最左列
                if (col == (n - 1))
                    col = 0;
                //否则往右一列
                else
                    col++;
            }
        }
        //输出生成的幻方
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(OutputFile));
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++){
                    bw.write(magic[i][j] + "\t");
                }
                bw.write("\n");
            }
            bw.close();
        }catch (IOException e){System.err.println("读取文件错误: " + e.getMessage());}

        return true;
    }


}
