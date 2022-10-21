package com.example.district13.DataModel;

import java.net.URL;

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

        public ValueUnit(int value, String unit) {
            this.value = value;
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

        public Method(MashTemp mash_temp, Fermentation fermentation, String twist) {
            this.mash_temp = mash_temp;
            this.fermentation = fermentation;
            this.twist = twist;
        }
    }

    private class MashTemp {
        private MashTempBody[] mash_temp;

        public MashTempBody[] getMash_temp() {
            return mash_temp;
        }

        public void setMash_temp(MashTempBody[] mash_temp) {
            this.mash_temp = mash_temp;
        }

        public MashTemp(MashTempBody[] mash_temp) {
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

        public MashTempBody(ValueUnit temp, int duration) {
            this.temp = temp;
            this.duration = duration;
        }
    }

    private class Fermentation {
        private ValueUnit temp;

        public ValueUnit getTemp() {
            return temp;
        }

        public void setTemp(ValueUnit temp) {
            this.temp = temp;
        }

        public Fermentation(ValueUnit temp) {
            this.temp = temp;
        }

    }

    private class Ingredient {
        private Malt malt;
        private Hops hops;
        private String yeast;

        public Malt getMalt() {
            return malt;
        }

        public void setMalt(Malt malt) {
            this.malt = malt;
        }

        public Hops getHops() {
            return hops;
        }

        public void setHops(Hops hops) {
            this.hops = hops;
        }

        public String getYeast() {
            return yeast;
        }

        public void setYeast(String yeast) {
            this.yeast = yeast;
        }

        public Ingredient(Malt malt, Hops hops, String yeast) {
            this.malt = malt;
            this.hops = hops;
            this.yeast = yeast;
        }
    }

    private class Malt {
        private MaltBody[] malt;

        public MaltBody[] getMalt() {
            return malt;
        }

        public void setMalt(MaltBody[] malt) {
            this.malt = malt;
        }

        public Malt(MaltBody[] malt) {
            this.malt = malt;
        }
    }

    private class MaltBody {
        private String name;
        private ValueUnit amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ValueUnit getAmount() {
            return amount;
        }

        public void setAmount(ValueUnit amount) {
            this.amount = amount;
        }

        public MaltBody(String name, ValueUnit amount) {
            this.name = name;
            this.amount = amount;
        }
    }

    private class Hops {
        private HopsBody[] hops;

        public HopsBody[] getHops() {
            return hops;
        }

        public void setHops(HopsBody[] hops) {
            this.hops = hops;
        }

        public Hops(HopsBody[] hops) {
            this.hops = hops;
        }
    }

    private class HopsBody {
        private String name;
        private ValueUnit amount;
        private String add;
        private String attribute;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ValueUnit getAmount() {
            return amount;
        }

        public void setAmount(ValueUnit amount) {
            this.amount = amount;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public HopsBody(String name, ValueUnit amount, String add, String attribute) {
            this.name = name;
            this.amount = amount;
            this.add = add;
            this.attribute = attribute;
        }
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
    private String brewers_tips;
    private String contribute_by;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getFirst_brewed() {
        return first_brewed;
    }

    public void setFirst_brewed(String first_brewed) {
        this.first_brewed = first_brewed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getImage_url() {
        return image_url;
    }

    public void setImage_url(URL image_url) {
        this.image_url = image_url;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public double getTarget_fg() {
        return target_fg;
    }

    public void setTarget_fg(double target_fg) {
        this.target_fg = target_fg;
    }

    public double getTarget_og() {
        return target_og;
    }

    public void setTarget_og(double target_og) {
        this.target_og = target_og;
    }

    public double getEbc() {
        return ebc;
    }

    public void setEbc(double ebc) {
        this.ebc = ebc;
    }

    public double getSrm() {
        return srm;
    }

    public void setSrm(double srm) {
        this.srm = srm;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getAttenuation_level() {
        return attenuation_level;
    }

    public void setAttenuation_level(double attenuation_level) {
        this.attenuation_level = attenuation_level;
    }

    public ValueUnit getVolume() {
        return volume;
    }

    public void setVolume(ValueUnit volume) {
        this.volume = volume;
    }

    public ValueUnit getBoil_volume() {
        return boil_volume;
    }

    public void setBoil_volume(ValueUnit boil_volume) {
        this.boil_volume = boil_volume;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Ingredient getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getFood_pairing() {
        return food_pairing;
    }

    public void setFood_pairing(String[] food_pairing) {
        this.food_pairing = food_pairing;
    }

    public String getBrewers_tips() {
        return brewers_tips;
    }

    public void setBrewers_tips(String brewers_tips) {
        this.brewers_tips = brewers_tips;
    }

    public String getContribute_by() {
        return contribute_by;
    }

    public void setContribute_by(String contribute_by) {
        this.contribute_by = contribute_by;
    }

    public JSONResponse(int id, String name, String tagline, String first_brewed, String description,
                        URL image_url, double abv, double ibu, double target_fg, double target_og,
                        double ebc, double srm, double ph, double attenuation_level, ValueUnit volume,
                        ValueUnit boil_volume, Method method, Ingredient ingredients,
                        String[] food_pairing, String brewers_tips, String contribute_by) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.first_brewed = first_brewed;
        this.description = description;
        this.image_url = image_url;
        this.abv = abv;
        this.ibu = ibu;
        this.target_fg = target_fg;
        this.target_og = target_og;
        this.ebc = ebc;
        this.srm = srm;
        this.ph = ph;
        this.attenuation_level = attenuation_level;
        this.volume = volume;
        this.boil_volume = boil_volume;
        this.method = method;
        this.ingredients = ingredients;
        this.food_pairing = food_pairing;
        this.brewers_tips = brewers_tips;
        this.contribute_by = contribute_by;
    }
}
