package com.shebut_dev.photoeditor.core;

public class MatrixFilterProvider {
    public static final float[][] MATRIX_FILTER_HIGHER_CLARITY =
                   {{-1, -1, -1},
                    {-1,  9, -1},
                    {-1, -1, -1}};

    public static final float[][] MATRIX_FILTER_HIGHER_BLIK =
            {{0, 0, 1, 0, 0},
                    {0,  1, 1, 1, 0},
                    {1, 1, 1, 1, 1},
                    {0, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0}};
    public static final float[][] MATRIX_FILTER_TEST =
            {{-3, -10, -3},
                    {0,  0, 0},
                    {3, 10, -3}};
}
