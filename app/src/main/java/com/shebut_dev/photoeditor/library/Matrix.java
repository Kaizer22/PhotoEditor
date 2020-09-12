package com.shebut_dev.photoeditor.library;

import java.util.List;

public class Matrix<T>
{
    protected T[][] elements;
    protected int size;

    protected int height;
    protected int width;

    public int getHeight()
    {
        return height;
    }

    public int getSize()
    {
        return size;
    }

    public int getWidth()
    {
        return width;
    }

    protected Type type = Type.NONE;
    //Тип матрицы; может быть использован для упрощения распознавания исключительных ситуаций
    public enum Type
    {
        SQUARE,
        RECTANGULAR,
        TRIANGULAR,
        SQUARE_TRIANGULAR,
        RECTANGULAR_TRIANGULAR,
        NONE
    }

    public Matrix(int size){
        type = Type.SQUARE;
        this.size = size;
        height = size;
        width = size;
        //elements = new T[size][size];
    }

    public Matrix(int height, int width)
    {
        if (height == width)
        {
            type = Type.SQUARE;
            size = width;
        }
        else
            type = Type.RECTANGULAR;

        this.height = height;
        this.width = width;
        //elements = new T[height][width];
    }

    public void setElement(int i, int j, T n)
    {
        elements[i][j] = n;
    }

    public T getElement(int i, int j)
    {
        return elements[i][j];
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    //Заполнение матрицы заданным значением
    public void fill(T value)
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                elements[i][j] = value;
            }
        }
    }


    //Возвращает матрицу, лежащую в прямоугольнике с диагональю x1y1-x2y2
    public Matrix<T> getArea(int x1, int y1, int x2, int y2)
    {
        Matrix<T> result = new Matrix<T>(y2 - y1 + 1, x2 - x1 + 1);

        for (int i = y1; i <= y2; i++)
            for (int j = x1; j <= x2; j++)
                result.setElement(i - y1, j - x1, elements[i][j]);

        return result;
    }

   // public T[] GetRow(int number)
   // {
        //T[] result = new T[width];
        //for (int i = 0; i<width; i++)
        //{
            //result[i] = elements[number][i];
        //}
       // return result;
    //}

    public Matrix<T> ConcatHorizontally(Matrix<T> first, Matrix<T> second)
    {
        Matrix<T> splittedMatrix = new Matrix<T>(first.height, first.width + second.width);
        return splittedMatrix;
    }

    public Matrix<T> ConcatHorizontally(Matrix<T> first, List<T> second)
    {
        Matrix<T> splittedMatrix = new Matrix<T>(first.height, first.width + 1);
        return splittedMatrix;
    }

    @Override
    public String toString(){
        String result = "";
        for (int i = 0; i < height; i++)
        {
            result += "|";
            for (int j = 0; j < width; j++)
                result += String.format("{0,20}", elements[i][j].toString());
            result += "|\n";
        }
        return result;
    }
}
