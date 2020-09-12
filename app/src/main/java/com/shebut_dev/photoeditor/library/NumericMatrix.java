package com.shebut_dev.photoeditor.library;

import android.util.Log;

import com.shebut_dev.photoeditor.library.exceptions.ZeroDetException;

import java.util.List;

public class NumericMatrix extends Matrix<Float> {
    //Конструктор квадратной матрицы
    public NumericMatrix(int matrix_size){
        super(matrix_size);
        elements = new Float[matrix_size][matrix_size];
    }
    //Конструктор прямоугольной матрицы
    public NumericMatrix( int height, int width) {
        super(height, width);
        elements = new Float[height][width];
    }


    public double[] getRow(int number)
    {
        double[] result = new double[width];
        for (int i = 0; i < width; i++)
        {
            result[i] = elements[number][i];
        }
        return result;
    }

    public  NumericMatrix concatHorizontally(NumericMatrix first, NumericMatrix second)
    {
        int newWidth = first.width + second.width;
        NumericMatrix concatenatedMatrix = new NumericMatrix(first.height, newWidth);

        for(int i = 0; i < first.height; i++)
        {
            for(int j = 0; j < newWidth; j++)
            {
                if (j < first.width)
                {
                    concatenatedMatrix.setElement(i, j, first.getElement(i,j));
                }
                else
                {
                    concatenatedMatrix.setElement(i, j, second.getElement(i, j - first.width));
                }

            }
        }

        return concatenatedMatrix;
    }

    public NumericMatrix concatHorizontally(NumericMatrix first, List<Float> second)
    {
        int newWidth = first.width + 1;
        NumericMatrix concatenatedMatrix = new NumericMatrix(first.height, newWidth);

        for (int i = 0; i < first.height; i++)
        {
            for (int j = 0; j < newWidth; j++)
            {
                if (j < first.width)
                {
                    concatenatedMatrix.setElement(i, j, first.getElement(i, j));
                }
                else
                {
                    concatenatedMatrix.setElement(i, j, second.get(i));
                }

            }
        }
        return concatenatedMatrix;
    }


    //Умножение матрицы на число
    public void multiple(float k)
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                elements[i][j] = elements[i][j] * k;
            }
        }
    }

    //Метод возвращает матрицу - результат перемножения matrix2 с данной
    public NumericMatrix multiple(NumericMatrix matrix2)
    {
        if (matrix2.size == size)
        {
            NumericMatrix result = new NumericMatrix(size);
            float buf = 0;

            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    for (int k = 0; k < width; k++)
                    {
                        buf += this.elements[i][k] * matrix2.getElement(k, j);

                    }
                    result.setElement(i, j, buf);
                    buf = 0;
                }
            }

            return result;
        }
        else
        {
            Log.e("NUM_MATRIX", "ERROR using NumericMatrix.Multiply(NumericMatrix matrix)");
            return null;
        }
    }




    //Метод, возвращающий определитель переданной матрицы
    public static double getDeterminant(NumericMatrix matrix)
    {
        int size = matrix.size;
        if (size == 1) return matrix.getElement(0,0);

        else if (size == 2)
            return matrix.getElement(0,0) * matrix.getElement(1,1) - matrix.getElement(0, 1) * matrix.getElement(1, 0);

        else
        {
            double det = 0;
            for (int j = 0; j < size; j++)
            {
                det += Math.pow(-1, j) * matrix.getElement(0, j) * getDeterminant(getMinorMatrixOfElement(matrix, 0, j));
            }
            return det;
        }
    }

    //Метод, осуществляющий сложение данной матрицы с переданной в метод, в случае если размерность матриц совпадает
    public void plus(NumericMatrix matrix2)
    {
        if (matrix2.width == width && matrix2.height == height)
        {
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    elements[i][j] += matrix2.getElement(i, j);
                }
            }
        }
        else Log.e("NUM_MATRIX","ERROR using NumericMatrix.Plus(NumericMatrix matrix)");

    }



    //Метод, преобразующий матрицу к треугольному виду и возвращающий ее ранг
    public static int getRank(NumericMatrix matrix)
    {
        NumericMatrix.toTriangular(matrix);
        int rank = 0;
        boolean is_empty = true;
        for(int i = 0; i < matrix.height; i++)
        {
            for(int j = 0; j < matrix.width; j++)
            {
                if (matrix.getElement(i, j) != 0)
                {
                    is_empty = false;
                    break;
                }

            }
            if (!is_empty) rank++;
            else break;
            is_empty = true;

        }
        return rank;
    }

    //Метод, преобразующий матрицу к треугольному виду путем последовательного вычитания строк
    public static void toTriangular(NumericMatrix matrix)
    {
        int j = 0;
        int i = 0;
        int h;
        float coeff = 0;

        for (; i < matrix.height-1; i++)
        {
            if  (matrix.getElement(i, j) != 0) matrix.divideRow(i, matrix.getElement(i, j));
            for (int k = i + 1; k < matrix.height; k++)
            {
                if ( matrix.getElement(i, j) == 0)
                {
                    h = j;
                    while (matrix.getElement(i, h) == 0 && h < matrix.width)
                        h++;
                    if (h != matrix.width)
                        coeff = -(matrix.getElement(k, j) / matrix.getElement(i, h));

                }
                else {
                    coeff = -(matrix.getElement(k, j) / matrix.getElement(i, j));
                }

                matrix.sumRows(k, i, coeff);
                //Console.WriteLine(matrix.ToString());
            }
            j++;

        }
        if (matrix.getElement(i, j) != 0) matrix.divideRow(i, matrix.getElement(i, j));
        //CheckRowsEquality(matrix);
        matrix.setType(Type.TRIANGULAR);
    }

    private static void checkRowsEquality(NumericMatrix matrix)
    {
        //double coeff =
        for (int i = 0; i < matrix.height; i++)
        {
            for (int k = i; k < matrix.height; k++)
            {
                for (int j = 0; j < matrix.width; j++) { }



            }
        }

    }

    //Метод, возвращающий обратную матрицу к переданной в метод матрице
    public static NumericMatrix getInverseMatrix(NumericMatrix matrix)
    {
        int size = matrix.size;
        NumericMatrix result = new NumericMatrix(size);

        try {
            double det = getDeterminant(matrix);

            if (det == 0)  throw new ZeroDetException("Исходная матрица вырожденная");


            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    result.setElement(j, i, (float) (Math.pow(-1, i + j) * getDeterminant(getMinorMatrixOfElement(matrix, i, j))));
                }
            }
            result.multiple((float) (1 / det));

        }
        catch (ZeroDetException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }


    //Метод, возвращающий матрицу минора указанного элемента заданной матрицы matrix
    public static NumericMatrix getMinorMatrixOfElement(NumericMatrix matrix, int i, int j)
    {
        int size = matrix.size;
        NumericMatrix result = new NumericMatrix(size - 1 );
        int x = 0, y = 0;
        for (int k = 0; k < size; k++)
        {
            if (k != i)
            {
                for (int t = 0; t < size; t++)
                {

                    if (t != j)
                    {
                        result.setElement(y, x, matrix.getElement(k, t));
                        x++;
                    }
                }
                y++;
                x = 0;
            }

        }
        //Console.WriteLine(result.ToString());
        return result;
    }




    // Метод, производящий элементарное преобразование матрицы - сложение строк с умножением на коэффициент
    public void sumRows(int row_number_to, int row_number_which, float coeff)
    {
        for (int j = 0; j < width; j++) {
            elements[row_number_to][j] += elements[row_number_which][j] * coeff;
        }
    }

    //Деление строки на заданное число
    public void divideRow(int row_number, float num)
    {
        for (int j = 0; j < width; j++)
            elements[row_number][j] /= num;

    }


    public String toString()
    {
        String result = "";
        for (int i = 0; i < height; i++)
        {
            //result += "|";

            for (int j = 0; j < width; j++)
                //result += String.format("{0,9}", Math.round(elements[i][j], 2));

            //result += "|";
            result +="\n";
        }

        return result;
    }
}
