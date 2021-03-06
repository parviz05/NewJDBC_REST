package RestAsurePractices;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Spartan2 {


    /*
    POJO
    Plain Old Java Object

     */

        private int id;
        private String name;
        private String gender;
        private long phone;

        public Spartan2() {

        }

        public Spartan2(String name, String gender, long phone) {
            this.name = name;
            this.gender = gender;
            this.phone = phone;
            this.id = id;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        @JsonIgnore//this will enable us to ignore Id field being written into json
                   // this will happen when u do serialization
        public int getId() {
            return id;
        }

        @JsonProperty//this will specifically tell to write this into pojo from json
                     // this will happen when u do serialization
        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Spartan{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", phone=" + phone +
                    '}';

    }
}
