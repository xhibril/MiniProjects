export const PLANETDATA = {
    Sun: {
        Image: "images/planets/sun.png",
        Audio: "audio/sun.mp3",
        AudioDesc: "Playing audio of the Sun",
        Title: "SUN",
        Desc: "The Sun is the central star of the solar system It produces energy through nuclear fusion in its core. " +
             "Its gravitational force keeps the planets in orbit. " ,

        Type: "Yellow dwarf star",
        Distance: "149.6 million km from Earth",
        Diameter: "1.39 million km",
        Temperature: "5,500°C",
        Fact: "The Sun contains over 99.8% of the total mass of the solar system."
    },

    Mercury: {
        Image: "images/planets/mercury.png",
        Audio: "audio/mercury.mp3",
        AudioDesc: "Playing audio of Mercury",
        Title: "MERCURY",
        Desc: "Mercury is the smallest planet in the solar system and the closest to the Sun. It has a rocky surface with extreme temperature variations due to its thin atmosphere." +
              " Mercury completes one orbit around the Sun in approximately 88 Earth days.",

        Type: "Terrestrial planet",
        Distance: "77 million km from Earth",
        Diameter: "4,879 km",
        Temperature: "430°C (day) / −180°C (night)",
        Fact: "A year on Mercury is shorter than a single day on the planet."
    },

    Venus: {
        Image: "images/planets/venus.png",
        Audio: "audio/venus.mp3",
        AudioDesc: "Playing audio of Venus",
        Title: "VENUS",
        Desc: "Venus is the second planet from the Sun and similar in size to Earth. It has a dense atmosphere rich in carbon dioxide, causing an extreme greenhouse effect." + 
            " Venus has the highest average surface temperature of any planet in the solar system.",

        Type: "Terrestrial planet",
        Distance: "261 million km from Earth",
        Diameter: "12,104 km",
        Temperature: "465°C",
        Fact: "Venus rotates in the opposite direction to most planets in the solar system."
    },

    Earth: {
        Image: "images/planets/earth.png",
        Audio: "audio/earth.mp3",
        AudioDesc: "Playing audio of Earth",

        Title: "EARTH",
        Desc: "Earth is the third planet from the Sun and the only known planet to support life. It has liquid water on its surface and a protective atmosphere rich in nitrogen and oxygen." + 
        " Earth completes one orbit around the Sun in approximately 365 days.",

        Type: "Terrestrial planet",
        Distance: "0 km (home)",
        Diameter: "12,742 km",
        Temperature: "15°C (average)",
        Fact: "Earth is the only known planet with stable liquid water on its surface."
    },

    Mars: {
        Image: "images/planets/mars.png",
        Audio: "audio/mars.mp3",
        AudioDesc: "Playing audio of Mars",

        Title: "MARS",
        Desc: "Mars is the fourth planet from the Sun and is often called the Red Planet. It has a thin atmosphere and surface features such as volcanoes, valleys, and polar ice caps." + 
               " Mars is a primary target for scientific exploration due to evidence of past water.",

        Type: "Terrestrial planet",
        Distance: "225 million km from Earth",
        Diameter: "6,779 km",
        Temperature: "−63°C",
        Fact: "Mars has the largest volcano in the solar system."
    },

    Jupiter: {
        Image: "images/planets/jupiter.png",
        Audio: "audio/jupiter.mp3",
        AudioDesc: "Playing audio of Jupiter",

        Title: "JUPITER",
        Desc: "Jupiter is the largest planet in the solar system and a gas giant. It is composed mainly of hydrogen and helium and has a powerful magnetic field." + 
                " Jupiter has dozens of moons and plays a major role in shaping the solar system.",

        Type: "Gas giant",
        Distance: "778 million km from Earth",
        Diameter: "139,820 km",
        Temperature: "−108°C",
        Fact: "Jupiter's gravity helps shield inner planets from frequent asteroid impacts."
    },

    Saturn: {
        Image: "images/planets/saturn.png",
        Audio: "audio/saturn.mp3",
        AudioDesc: "Playing audio of Saturn",
        
        Title: "SATURN",
        Desc: "Saturn is the sixth planet from the Sun and a gas giant known for its ring system. It is composed mainly of hydrogen and helium and has a low average density." +
               " Saturn has numerous moons, including Titan, which has a thick atmosphere.",

        Type: "Gas giant",
        Distance: "1.43 billion km from Earth",
        Diameter: "116,460 km",
        Temperature: "−139°C",
        Fact: "Saturn's density is low enough that it could float in water, hypothetically."
    },

    Uranus: {
        Image: "images/planets/uranus.png",
        Audio: "audio/uranus.mp3",
        AudioDesc: "Playing audio of Uranus",
        Title: "URANUS",
        Desc: "Uranus is the seventh planet from the Sun and an ice giant. It has a unique axial tilt that causes extreme seasonal variations." +
              " Uranus is composed mainly of water, ammonia, and methane ices.",

        Type: "Ice giant",
        Distance: "2.87 billion km from Earth",
        Diameter: "50,724 km",
        Temperature: "−195°C",
        Fact: "Uranus rotates on its side, resulting in extreme and long-lasting seasons."
    },

    Neptune: {
        Image: "images/planets/neptune.png",
        Audio: "audio/neptune.mp3",
        AudioDesc: "Playing audio of Neptune",
        Title: "NEPTUNE",
        Desc: "Neptune is the eighth and farthest known planet from the Sun. It is an ice giant with strong winds and dynamic storm systems." +
          " Neptune's deep blue color is caused by methane in its atmosphere.",

        Type: "Ice giant",
        Distance: "4.5 billion km from Earth",
        Diameter: "49,244 km",
        Temperature: "−201°C",
        Fact: "Neptune has the fastest recorded winds in the solar system."
    }
};



export const TOPICDATA = {
    GALAXY: {
    Title: "GALAXY",
    Desc:
      "Galaxies are massive systems made up of billions of stars, along with gas, dust, and dark matter, all held together by gravity.<br><br>" +
      "They form over long periods of time as matter gathers and collapses under gravitational forces.<br><br>" +
      "Galaxies come in different shapes, such as spiral, elliptical, and irregular.<br><br>" +
      "Most galaxies contain a supermassive black hole at their center, which influences the motion of nearby stars.<br><br>" +
      "Galaxies are the largest organized structures in the universe and contain most of its visible matter.",
      Image: "images/galaxy/galaxy.jpg"
  },

  BLACKHOLE: {
    Title: "BLACK HOLE",
    Desc:
      "Black holes are regions of space with extremely strong gravity caused by a large amount of mass compressed into a very small area.<br><br>" +
      "Most black holes form when massive stars collapse at the end of their life.<br><br>" +
      "Their gravity is so strong that not even light can escape, which is why they appear invisible.<br><br>" +
      "Although black holes cannot be seen directly, their presence is detected through their effects on nearby objects, such as stars orbiting them or gas heating up as it falls inward.<br><br>" +
      "Black holes also distort space and time due to their intense gravity.",
    Image: "images/black-hole/black-hole.png"
  },

  NEBULA: {
    Title: "NEBULA",
    Desc:
      "Nebulae are large clouds of gas and dust found in space, spread across vast regions between stars.<br><br>" +
      "They form when material from stars is released through stellar winds or explosions such as supernovae.<br><br>" +
      "Nebulae are often the birthplaces of new stars, where gravity slowly pulls gas and dust together.<br><br>" +
      "Although nebulae can appear faint, they glow when nearby stars heat the gas or when light reflects off surrounding dust.<br><br>" +
      "Nebulae play an important role in the life cycle of stars and the structure of galaxies.",
      Image: "images/nebula/nebula.jpg"
  }
};



export const BLACK_HOLES_CLOSEST = {
  GAIA_BH1: {
    name: "#1 Gaia BH1",
    image: "images/black-hole/closest-black-hole-1.webp",
    desc:
      "Gaia BH1 is one of the closest known black holes to Earth, discovered through precise measurements made by the Gaia space telescope.<br><br>" +
      "It was identified by observing the motion of a Sun-like star orbiting an invisible companion, confirming the presence of a black hole.<br><br>" +
      "Gaia BH1 does not emit X-rays, making it a rare example of a dormant black hole detected purely through gravitational effects.",
    type: "Stellar-mass black hole",
    distance: "1.56k light-years",
    diameter: "30 km"
  },

  A0620_00: {
    name: "#2 A0620−00",
    image: "images/black-hole/closest-black-hole-2.jpg",
    desc:
      "A0620−00 is a stellar-mass black hole located in the constellation Monoceros and is part of a binary star system.<br><br>" +
      "It was discovered due to strong X-ray emissions produced as matter from a companion star falls toward the black hole.<br><br>" +
      "This system has been studied extensively and provides important evidence for how black holes interact with nearby stars.",
    type: "Stellar-mass black hole",
    distance: "3.3k light-years",
    diameter: "20 km"
  },

  CYGNUS_X1: {
    name: "#3 Cygnus X-1",
    image: "images/black-hole/closest-black-hole-3.jpg",
    desc:
      "Cygnus X-1 is one of the first black hole candidates ever discovered and is located in the constellation Cygnus.<br><br>" +
      "It is part of a binary system where the black hole pulls material from a massive companion star, producing intense X-ray radiation.<br><br>" +
      "Cygnus X-1 played a major role in confirming the existence of stellar-mass black holes.",
    type: "Stellar-mass black hole",
    distance: "6.07k light-years",
    diameter: "40 km"
  },

  V404_CYGNI: {
    name: "#4 V404 Cygni",
    image: "images/black-hole/closest-black-hole-4.jpg",
    desc:
      "V404 Cygni is a black hole binary system known for its dramatic and irregular outbursts of energy.<br><br>" +
      "These outbursts occur when large amounts of material suddenly fall into the black hole from its companion star.<br><br>" +
      "It is one of the most studied black hole systems due to its extreme variability.",
    type: "Stellar-mass black hole",
    distance: "7.9k light-years",
    diameter: "25 km"
  },

  XTE_J1118_480: {
    name: "#5 XTE J1118+480",
    image: "images/black-hole/closest-black-hole-5.jpg",
    desc:
      "XTE J1118+480 is a black hole located high above the plane of the Milky Way galaxy.<br><br>" +
      "It was discovered through X-ray observations and is part of a binary system with a low-mass companion star.<br><br>" +
      "Its unusual position and motion suggest it may have been ejected from the galaxy’s disk after its formation.",
    type: "Stellar-mass black hole",
    distance: "6.1k light-years",
    diameter: "15 km"
  }
};



export const BLACK_HOLES_LARGEST = {
  Ton618: {
    name: "#1 TON 618",
    image: "images/black-hole/largest-black-hole-1.webp",
    desc:
      "TON 618 is an extremely massive supermassive black hole located at the center of a distant quasar billions of light-years from Earth.<br><br>" +
      "It is estimated to have a mass tens of billions of times greater than the Sun, making it one of the largest black holes ever discovered.<br><br>" +
      "TON 618 is observed through the intense radiation produced as surrounding gas and dust fall into it, forming a bright accretion disk.",
    type: "Supermassive black hole",
    distance: "10.4B light-years",
    diameter: "390M km"
  },

  PhoenixA: {
    name: "#2 Phoenix A",
    image: "images/black-hole/largest-black-hole-2.jpg",
    desc:
      "Phoenix A is a supermassive black hole located at the center of the Phoenix Cluster, one of the most massive galaxy clusters known.<br><br>" +
      "It is rapidly consuming surrounding matter, producing enormous amounts of energy and heat in its host galaxy cluster.<br><br>" +
      "This black hole plays a major role in regulating star formation by heating nearby gas and preventing it from cooling.",
    type: "Supermassive black hole",
    distance: "5.7B light-years",
    diameter: "240M km"
  },

  Holmberg15A: {
    name: "#3 Holmberg 15A",
    image: "images/black-hole/largest-black-hole-3.avif",
    desc:
      "The black hole in Holmberg 15A is located at the center of a massive elliptical galaxy in the Abell 85 galaxy cluster.<br><br>" +
      "It is estimated to be among the most massive black holes known, with a mass of tens of billions of solar masses.<br><br>" +
      "Its extreme size was determined by studying the motion of stars in the galaxy’s central region.",
    type: "Supermassive black hole",
    distance: "700M light-years",
    diameter: "300M km"
  },

  S5001481: {
    name: "#4 S5 0014+81",
    image: "images/black-hole/largest-black-hole-4.jpg",
    desc:
      "S5 0014+81 is a quasar powered by an exceptionally massive black hole located billions of light-years away.<br><br>" +
      "The black hole is surrounded by a bright accretion disk that emits enormous amounts of radiation across the universe.<br><br>" +
      "Its mass is estimated to be tens of billions of times that of the Sun, placing it among the largest known black holes.",
    type: "Supermassive black hole",
    distance: "12B light-years",
    diameter: "280M km"
  },

  NGC4889: {
    name: "#5 NGC 4889",
    image: "images/black-hole/largest-black-hole-5.jpg",
    desc:
      "The black hole at the center of NGC 4889 resides in a giant elliptical galaxy within the Coma Cluster.<br><br>" +
      "It has a mass estimated to be over twenty billion times that of the Sun, making it one of the most massive black holes measured locally.<br><br>" +
      "Astronomers determined its size by analyzing the velocities of stars orbiting near the galaxy’s core.",
    type: "Supermassive black hole",
    distance: "321M light-years",
    diameter: "180M km"
  }
};



export const GALAXY_CLOSEST = {
  CanisMajorDwarf: {
    name: "#1 Canis Major Dwarf Galaxy",
    image: "images/galaxy/closest-galaxy-1.png",
    desc:
      "The Canis Major Dwarf Galaxy is the closest known galaxy to the Milky Way, located only about 25,000 light-years from Earth.<br><br>" +
      "It is a small dwarf galaxy that is currently being torn apart by the Milky Way’s gravitational forces as it slowly merges with our galaxy.<br><br>" +
      "Astronomers identified it by observing an overdensity of stars hidden behind dense clouds of interstellar dust.",
    type: "Dwarf galaxy",
    distance: "25k light-years",
    diameter: "12k light-years"
  },

  SagittariusDwarf: {
    name: "#2 Sagittarius Dwarf Galaxy",
    image: "images/galaxy/closest-galaxy-2.webp",
    desc:
      "The Sagittarius Dwarf Spheroidal Galaxy is a small satellite galaxy orbiting the Milky Way at a distance of about 70,000 light-years.<br><br>" +
      "It is in the process of being disrupted and absorbed by the Milky Way, leaving behind long streams of stars across the sky.<br><br>" +
      "This galaxy has played an important role in shaping the structure of the Milky Way’s stellar halo.",
    type: "Dwarf spheroidal galaxy",
    distance: "70k light-years",
    diameter: "10k light-years"
  },

  LargeMagellanicCloud: {
    name: "#3 Large Magellanic Cloud",
    image: "images/galaxy/closest-galaxy-3.webp",
    desc:
      "The Large Magellanic Cloud is a nearby dwarf galaxy located roughly 163,000 light-years from Earth and visible to the naked eye from the Southern Hemisphere.<br><br>" +
      "It is actively forming stars and contains some of the brightest nebulae known, including the famous Tarantula Nebula.<br><br>" +
      "The Large Magellanic Cloud is gravitationally bound to the Milky Way and is slowly moving closer over time.",
    type: "Dwarf irregular galaxy",
    distance: "163k light-years",
    diameter: "14k light-years"
  },

  SmallMagellanicCloud: {
    name: "#4 Small Magellanic Cloud",
    image: "images/galaxy/closest-galaxy-4.webp",
    desc:
      "The Small Magellanic Cloud is a dwarf galaxy located about 200,000 light-years away and is a close companion of both the Milky Way and the Large Magellanic Cloud.<br><br>" +
      "It has an irregular shape due to gravitational interactions with its neighboring galaxies.<br><br>" +
      "This galaxy is rich in gas and dust, making it an important region for studying star formation and galactic evolution.",
    type: "Dwarf irregular galaxy",
    distance: "200k light-years",
    diameter: "7k light-years"
  },

  Andromeda: {
    name: "#5 Andromeda Galaxy (M31)",
    image: "images/galaxy/closest-galaxy-5.png",
    desc:
      "The Andromeda Galaxy is the closest major spiral galaxy to the Milky Way, located approximately 2.5 million light-years from Earth.<br><br>" +
      "It is larger and more massive than the Milky Way and contains over a trillion stars.<br><br>" +
      "Astronomers predict that Andromeda and the Milky Way will collide and merge in about four to five billion years.",
    type: "Spiral galaxy",
    distance: "2.5M light-years",
    diameter: "220k light-years"
  }
};



export const GALAXY_LARGEST = {
  IC1101: {
    name: "#1 IC 1101",
    image: "images/galaxy/largest-galaxy-1.webp",
    desc:
      "IC 1101 is the largest known galaxy by physical size, located at the center of the Abell 2029 galaxy cluster.<br><br>" +
      "It is a supergiant elliptical galaxy with a diameter of over six million light-years, vastly larger than the Milky Way.<br><br>" +
      "IC 1101 contains trillions of stars and is believed to have grown through numerous galaxy mergers over billions of years.",
    type: "Elliptical galaxy",
    distance: "1.04B light-years",
    diameter: "6M light-years"
  },

  PhoenixClusterGalaxy: {
    name: "#2 Phoenix Cluster Central Galaxy",
    image: "images/galaxy/largest-galaxy-2.jpg",
    desc:
      "The central galaxy of the Phoenix Cluster is an enormous elliptical galaxy located billions of light-years from Earth.<br><br>" +
      "It resides in one of the most massive galaxy clusters ever discovered and is surrounded by extremely hot gas emitting powerful X-rays.<br><br>" +
      "This galaxy is notable for its intense star formation, fueled by vast amounts of cooling gas in the cluster core.",
    type: "Elliptical galaxy",
    distance: "5.7B light-years",
    diameter: "3M light-years"
  },

  Holmberg15A: {
    name: "#3 Holmberg 15A",
    image: "images/galaxy/largest-galaxy-3.avif",
    desc:
      "Holmberg 15A is a massive elliptical galaxy located at the center of the Abell 85 galaxy cluster.<br><br>" +
      "It has an unusually large and diffuse core, indicating a violent history of mergers with other galaxies.<br><br>" +
      "The galaxy hosts one of the most massive known supermassive black holes at its center.",
    type: "Elliptical galaxy",
    distance: "700M light-years",
    diameter: "2M light-years"
  },

  NGC262: {
    name: "#4 NGC 262",
    image: "images/galaxy/largest-galaxy-4.jpg",
    desc:
      "NGC 262 is a giant spiral galaxy with an enormous disk extending over one million light-years in diameter.<br><br>" +
      "It contains a bright active galactic nucleus, powered by a supermassive black hole at its core.<br><br>" +
      "The galaxy’s immense size makes it one of the largest spiral galaxies ever observed.",
    type: "Spiral galaxy",
    distance: "300M light-years",
    diameter: "1M light-years"
  },

  UGC2885: {
    name: "#5 UGC 2885",
    image: "images/galaxy/largest-galaxy-5.webp",
    desc:
      "UGC 2885 is a colossal spiral galaxy often referred to as a ‘giant low surface brightness galaxy.’<br><br>" +
      "It is more than twice the diameter of the Milky Way and contains hundreds of billions of stars.<br><br>" +
      "Despite its massive size, the galaxy appears to have evolved relatively quietly, with few signs of major collisions.",
    type: "Spiral galaxy",
    distance: "232M light-years",
    diameter: "832k light-years"
  }
};



export const NEBULA_CLOSEST = {
  OrionNebula: {
    name: "#1 Orion Nebula (M42)",
    image: "images/nebula/closest-nebula-1.jpg",
    desc:
      "The Orion Nebula is the closest massive star-forming nebula to Earth, located approximately 1,344 light-years away in the constellation Orion.<br><br>" +
      "It is a vast region of gas and dust where new stars are actively forming, illuminated by intense ultraviolet radiation from young, hot stars.<br><br>" +
      "The Orion Nebula is visible to the naked eye and has been studied extensively as a key example of stellar birth.",
    type: "Emission nebula",
    distance: "1344 light-years",
    diameter: "24 light-years"
  },

  HelixNebula: {
    name: "#2 Helix Nebula",
    image: "images/nebula/closest-nebula-2.jpg",
    desc:
      "The Helix Nebula is one of the closest planetary nebulae to Earth, located about 650 light-years away in the constellation Aquarius.<br><br>" +
      "It formed when a dying Sun-like star expelled its outer layers, leaving behind a hot white dwarf at its center.<br><br>" +
      "Its distinctive ring-like structure makes it a classic example of a planetary nebula.",
    type: "Planetary nebula",
    distance: "650 light-years",
    diameter: "2.5 light-years"
  },

  GumNebula: {
    name: "#3 Gum Nebula",
    image: "images/nebula/closest-nebula-3.jpg",
    desc:
      "The Gum Nebula is a large and diffuse emission nebula located roughly 1,500 light-years from Earth.<br><br>" +
      "It is thought to be the remains of an ancient supernova explosion or the result of stellar winds from massive stars.<br><br>" +
      "Due to its low surface brightness, the Gum Nebula is difficult to observe despite its enormous size.",
    type: "Emission nebula",
    distance: "1500 light-years",
    diameter: "300 light-years"
  },

  RosetteNebula: {
    name: "#4 Rosette Nebula",
    image: "images/nebula/closest-nebula-4.jpg",
    desc:
      "The Rosette Nebula is a vast star-forming region located approximately 5,200 light-years away in the constellation Monoceros.<br><br>" +
      "It contains a young open star cluster whose radiation shapes and ionizes the surrounding gas clouds.<br><br>" +
      "The nebula provides important insight into how massive stars influence their birth environments.",
    type: "Emission nebula",
    distance: "5200 light-years",
    diameter: "130 light-years"
  },

  LagoonNebula: {
    name: "#5 Lagoon Nebula (M8)",
    image: "images/nebula/closest-nebula-5.png",
    desc:
      "The Lagoon Nebula is a large emission nebula located about 4,100 light-years from Earth in the constellation Sagittarius.<br><br>" +
      "It is an active stellar nursery containing dense gas clouds, young stars, and dark dust lanes.<br><br>" +
      "The nebula is one of the brightest star-forming regions visible from Earth.",
    type: "Emission nebula",
    distance: "4100 light-years",
    diameter: "110 light-years"
  }
};



export const NEBULA_LARGEST = {
  TarantulaNebula: {
    name: "#1 Tarantula Nebula",
    image: "images/nebula/largest-nebula-1.jpg",
    desc:
      "The Tarantula Nebula is the largest known star-forming nebula in the Local Group of galaxies, spanning over 1,000 light-years in diameter.<br><br>" +
      "Located in the Large Magellanic Cloud, it contains some of the most massive and luminous stars ever discovered.<br><br>" +
      "Its extreme energy output makes it one of the most active stellar nurseries known.",
    type: "Emission nebula",
    distance: "163k light-years",
    diameter: "1000 light-years"
  },

  GumNebula: {
    name: "#2 Gum Nebula",
    image: "images/nebula/largest-nebula-2.jpg",
    desc:
      "The Gum Nebula is one of the largest emission nebulae known, stretching across hundreds of light-years of space.<br><br>" +
      "It is believed to be the remnant of multiple supernova explosions or powerful stellar winds from massive stars.<br><br>" +
      "Despite its size, it is faint and difficult to detect without specialized imaging.",
    type: "Emission nebula",
    distance: "1500 light-years",
    diameter: "300 light-years"
  },

  LambdaOrionisRing: {
    name: "#3 Lambda Orionis Ring",
    image: "images/nebula/largest-nebula-3.jpg",
    desc:
      "The Lambda Orionis Ring is a massive ring-shaped nebula surrounding the star Meissa in the Orion constellation.<br><br>" +
      "It spans hundreds of light-years and is thought to have formed from a past supernova event.<br><br>" +
      "The structure provides evidence of how stellar explosions reshape interstellar space.",
    type: "Supernova remnant",
    distance: "1300 light-years",
    diameter: "300 light-years"
  },

  NorthAmericaNebula: {
    name: "#4 North America Nebula",
    image: "images/nebula/largest-nebula-4.jpg",
    desc:
      "The North America Nebula is a huge emission nebula with a shape resembling the North American continent.<br><br>" +
      "It spans hundreds of light-years and is located about 2,600 light-years from Earth in the constellation Cygnus.<br><br>" +
      "The nebula is part of a much larger complex of gas and dust associated with ongoing star formation.",
    type: "Emission nebula",
    distance: "2600 light-years",
    diameter: "140 light-years"
  },

  VelaSupernovaRemnant: {
    name: "#5 Vela Supernova Remnant",
    image: "images/nebula/largest-nebula-5.jpg",
    desc:
      "The Vela Supernova Remnant is the expanding debris cloud left behind by a massive star that exploded thousands of years ago.<br><br>" +
      "It spans roughly 100 light-years and contains complex filaments of hot gas and shock waves.<br><br>" +
      "This nebula provides valuable insight into the aftermath of supernova explosions and their impact on the surrounding interstellar medium.",
    type: "Supernova remnant",
    distance: "800 light-years",
    diameter: "100 light-years"
  }
};
