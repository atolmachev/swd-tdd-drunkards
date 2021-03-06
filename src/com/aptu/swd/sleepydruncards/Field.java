package com.aptu.swd.sleepydruncards;


import java.util.Arrays;

import static java.util.stream.Collectors.reducing;

public class Field {
  private final Figure[][] figures;

  public Field(int width, int height) {
    figures = new Figure[width][height];
  }

  public void put(Figure figure, int x, int y) {
    Point point = findDrunkardPosition(figure);
    if (figures[x][y] != null || point != null) {
      throw new IllegalArgumentException();
    }
    figures[x][y] = figure;
  }

  private Point findDrunkardPosition(Figure figure) {
    for (int i = 0; i < figures.length; i++) {
      for (int j = 0; j < figures[i].length; j++) {
        if (figures[i][j] == figure) {
          return new Point(i, j);
        }
      }
    }
    return null;
  }

  public Figure get(int x, int y) {
    return figures[x][y];
  }

  public void move(Figure figure, int newX, int newY) {
    Point p = findDrunkardPosition(figure);
    if (p == null || !isMovePossible(p, new Point(newX, newY))) {
      throw new IllegalArgumentException();
    }

    figures[p.x][p.y] = null;
    figures[newX][newY] = figure;
  }

  private boolean isMovePossible(Point from, Point to) {
    return (figures[to.x][to.y] == null || figures[to.x][to.y] instanceof Column) &&
        (Math.abs(from.x - to.x) + Math.abs(from.y - to.y) <= 1);
  }

  public void print() {
    for (Figure[] figure : figures) {
      String s = Arrays.stream(figure).map(f -> (f != null) ? f.getAvatar() : '.')
          .collect(reducing("", String::valueOf, (s1, s2) -> s1 + s2));
      System.out.println(s);
    }
  }
}