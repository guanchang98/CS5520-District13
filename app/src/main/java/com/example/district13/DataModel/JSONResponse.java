package com.example.district13.DataModel;

import java.net.URL;
import java.util.List;

public class JSONResponse {
    private class ValueUnit {
        private int value;
        private String unit;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    private class Method {
        private MashTemp mash_temp;
        private Fermentation fermentation;
        private String twist;

        public MashTemp getMash_temp() {
            return mash_temp;
        }

        public void setMash_temp(MashTemp mash_temp) {
            this.mash_temp = mash_temp;
        }

        public Fermentation getFermentation() {
            return fermentation;
        }

        public void setFermentation(Fermentation fermentation) {
            this.fermentation = fermentation;
        }

        public String getTwist() {
            return twist;
        }

        public void setTwist(String twist) {
            this.twist = twist;
        }
    }

    private class MashTemp {
        List<MashTempBody> mash_temp;

        public List<MashTempBody> getMash_temp() {
            return mash_temp;
        }

        public void setMash_temp(List<MashTempBody> mash_temp) {
            this.mash_temp = mash_temp;
        }
    }

    private class MashTempBody {
        private ValueUnit temp;
        private int duration;

        public ValueUnit getTemp() {
            return temp;
        }

        public void setTemp(ValueUnit temp) {
            this.temp = temp;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    private class Fermentation {
        ValueUnit temp;

        public ValueUnit getTemp() {
            return temp;
        }

        public void setTemp(ValueUnit temp) {
            this.temp = temp;
        }
    }

    private class Ingredient {
        Malt malt;
        Hops hops;
        String yeast;
    }

    private class Malt {
        List<MaltBody> malt;
    }

    private class MaltBody {
        String name;
        ValueUnit amount;
    }

    private class Hops {
        List<HopsBody> hops;
    }

    private class HopsBody {
        String name;
        ValueUnit amount;
        String add;
        String attribute;
    }


    /*
    "id": 192,
    "name": "Punk IPA 2007 - 2010",
    "tagline": "Post Modern Classic. Spiky. Tropical. Hoppy.",
    "first_brewed": "04/2007",
    "description": "Our flagship beer that kick started the craft beer revolution. This is James and Martin's original take on an American IPA, subverted with punchy New Zealand hops. Layered with new world hops to create an all-out riot of grapefruit, pineapple and lychee before a spiky, mouth-puckering bitter finish.",
    "image_url": "https://images.punkapi.com/v2/192.png",
    "abv": 6.0,
    "ibu": 60.0,
    "target_fg": 1010.0,
    "target_og": 1056.0,
    "ebc": 17.0,
    "srm": 8.5,
    "ph": 4.4,
    "attenuation_level": 82.14,
    "volume": {
      "value": 20,
      "unit": "liters"
    },
    "boil_volume": {
      "value": 25,
      "unit": "liters"
    },
    "method": {
      "mash_temp": [
        {
          "temp": {
            "value": 65,
            "unit": "celsius"
          },
          "duration": 75
        }
      ],
      "fermentation": {
        "temp": {
          "value": 19.0,
          "unit": "celsius"
        }
      },
      "twist": null
    },
    "ingredients": {
      "malt": [
        {
          "name": "Extra Pale",
          "amount": {
            "value": 5.3,
            "unit": "kilograms"
          }
        }
      ],
      "hops": [
        {
          "name": "Ahtanum",
          "amount": {
            "value": 17.5,
            "unit": "grams"
           },
           "add": "start",
           "attribute": "bitter"
         },
         {
           "name": "Chinook",
           "amount": {
             "value": 15,
             "unit": "grams"
           },
           "add": "start",
           "attribute": "bitter"
         },
         ...
      ],
      "yeast": "Wyeast 1056 - American Ale™"
    },
    "food_pairing": [
      "Spicy carne asada with a pico de gallo sauce",
      "Shredded chicken tacos with a mango chilli lime salsa",
      "Cheesecake with a passion fruit swirl sauce"
    ],
    "brewers_tips": "While it may surprise you, this version of Punk IPA isn't dry hopped but still packs a punch! To make the best of the aroma hops make sure they are fully submerged and add them just before knock out for an intense hop hit.",
    "contributed_by": "Sam Mason <samjbmason>"
    */
    private int id;
    private String name;
    private String tagline;
    private String first_brewed;
    private String description;
    private URL image_url;
    private double abv;
    private double ibu;
    private double target_fg;
    private double target_og;
    private double ebc;
    private double srm;
    private double ph;
    private double attenuation_level;
    private ValueUnit volume;
    private ValueUnit boil_volume;
    private Method method;
    private Ingredient ingredients;
    private String[] food_pairing;
    String brewers_tips;
    String contribute_by;
}
