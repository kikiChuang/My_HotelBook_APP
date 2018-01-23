package br.liveo.hotelbook.hotelbook.city;

/**
 * Created by phx on 2016/1/29.
 */
public class City {
    public String countryName, countryChName, cityName, cityChName;
    public int cityId;

    private City(Builder builder){
        countryName = builder.countryName;
        countryChName = builder.countryChName;
        cityName = builder.cityName;
        cityChName = builder.cityChName;
        cityId = builder.cityId;
    }

    public String getCountryName(){
        return countryName;
    }

    public String getCountryChName(){
        return countryChName;
    }

    public String getCityName(){
        return cityName;
    }

    public String getCityChName(){
        return cityChName;
    }

    public int getCityId(){
        return cityId;
    }

    public static class Builder{
        private String countryName, countryChName, cityName, cityChName;
        private int cityId;

        public Builder setCountryName(String countryName){
            this.countryName = countryName;
            return Builder.this;
        }

        public Builder setCountryChName(String countryChName){
            this.countryChName = countryChName;
            return Builder.this;
        }

        public Builder setCityName(String cityName){
            this.cityName = cityName;
            return Builder.this;
        }

        public Builder setCityChName(String cityChName){
            this.cityChName = cityChName;
            return Builder.this;
        }

        public Builder setCityId(int cityId){
            this.cityId = cityId;
            return Builder.this;
        }


        public City build(){
            return new City(Builder.this);
        }
    }
}

