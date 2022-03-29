package de.su.droneservice.drone.model;

public enum Direction {
    EAST(1,0),WEST(-1,0),SOUTH(0,-1),NORTH(0,1);

    private Integer X;
    private Integer Y;

    private Direction(Integer X, Integer Y){
        this.X = X;
        this.Y = Y;
    }

    public Integer getXMovement(){
        return X;
    }

    public Integer getYMovement(){
        return Y;
    }

    public Direction getLeft(){
        if(this.equals(EAST)){
            return NORTH;
        }
        else if(this.equals(NORTH)){
            return WEST;
        }
        else if(this.equals(WEST)){
            return SOUTH;
        }
        return EAST;
    }

    public Direction getRight(){
        if(this.equals(EAST)){
            return SOUTH;
        }
        else if(this.equals(NORTH)){
            return EAST;
        }
        else if(this.equals(WEST)){
            return NORTH;
        }
        return EAST;
    }



}
