package seedu.address.model.person;
//I am attempting to use the builder class to validate the Car object before it is returned.
//Should we reset the fields of the builder to null after initialising the object?

public class Car {
    private String color;
    private String wheel;

    private Car(String color, String wheel) {
        this.color = color;
        this.wheel = wheel;
    }

    //Getters
    String getColor() {
        return color;
    }
    
    String getWheel() {
        return wheel;
    }
    
    //Builder
    public static final class Builder {
        private String color;
        private String wheel;

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setWheel(String wheel) {
            this.wheel = wheel;
            return this;
        }


        public Car build() throws IllegalArgumentException{
            
            Car newCar = new Car(color, wheel);
            
            //Validate after object has been created as per stackOverflow link
            //https://stackoverflow.com/questions/38173274/builder-pattern-validation-effective-java
            //However this seems to be contrary to the answer in the following link
            //https://stackoverflow.com/questions/12930852/clearing-doubts-about-the-builder-pattern
            //However it seems safer to follow the first as the object fields could be mutated after it has been copied 
            // from the builder to the object.
            if(newCar.color == null || newCar.wheel == null) {
                throw new IllegalArgumentException("Color and wheel must be specified!");
            }
            
            //Question: Is this allowed? Should it be omitted?
            color = null;
            wheel = null;
            
            return newCar;
        }
    }
}

