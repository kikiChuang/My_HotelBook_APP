package br.liveo.hotelbook.hotelbook.hotels;

/**
 * Created by phx on 2016/1/26.
 */
public class Hotel {
    public String hotelName, hotelStar, hotelRoomType, hotelPriceType, hoteldetailLink, hotelImgFile;
    public int hotelId, hotelRoomPrice;


    private Hotel(Builder builder){
        hotelName = builder.hotelName;
        hotelStar = builder.hotelStar;
        hotelRoomType = builder.hotelRoomType;
        hotelRoomPrice = builder.hotelRoomPrice;
        hotelPriceType = builder.hotelPriceType;
        hoteldetailLink = builder.hoteldetailLink;
        hotelImgFile = builder.hotelImgFile;
        hotelId = builder.hotelId;
    }

    public String getHotelName(){
        return hotelName;
    }

    public String getHotelStar(){
        return hotelStar;
    }

    public String getHotelRoomType(){
        return hotelRoomType;
    }

    public int getHotelRoomPrice(){
        return hotelRoomPrice;
    }

    public String getHotelPriceType(){
        return hotelPriceType;
    }

    public String getHotelDetailLink(){
        return hoteldetailLink;
    }

    public String getHotelImgFile(){
        return hotelImgFile;
    }

    public int getHotelId(){
        return hotelId;
    }


    public static class Builder{
        public String hotelName, hotelStar, hotelRoomType, hotelPriceType, hoteldetailLink, hotelImgFile;
        public int hotelId, hotelRoomPrice;

        public Builder setHotelName(String hotelName){
            this.hotelName = hotelName;
            return Builder.this;
        }

        public Builder setHotelStar(String hotelStar){
            this.hotelStar = hotelStar;
            return Builder.this;
        }

        public Builder setHotelRoomType(String hotelRoomType){
            this.hotelRoomType = hotelRoomType;
            return Builder.this;
        }

        public Builder setHotelRoomPrice(int hotelRoomPrice){
            this.hotelRoomPrice = hotelRoomPrice;
            return Builder.this;
        }

        public Builder setHotelPriceType(String hotelPriceType){
            this.hotelPriceType = hotelPriceType;
            return Builder.this;
        }

        public Builder setHotelDetailLink(String hoteldetailLink){
            this.hoteldetailLink = hoteldetailLink;
            return Builder.this;
        }

        public Builder setHotelImgFile(String hotelImgFile){
            this.hotelImgFile = hotelImgFile;
            return Builder.this;
        }

        public Builder setHotelId(int hotelId){
            this.hotelId = hotelId;
            return Builder.this;
        }

        public Hotel build(){
            return new Hotel(Builder.this);
        }
    }
}
