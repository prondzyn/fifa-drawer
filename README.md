FIFA Drawer
===========
FIFA Drawer is a small application to draw participants and teams to play FIFA game on Xbox :)

Requirements
------------

### Required Files
FIFA Drawer requires three files:

1.  The main configuration file. See [example config file](config/example-fifa-drawer.cfg).
2.  A participants CSV file. You can find example [here](config/participants-example.csv).
3.  A teams CSV file. File for FIFA 12 is [here](data/fifa12-teams.csv). File for FIFA 12 after squads update is [here](data/fifa12-aisXbox-teams.csv).

A path to the main configuration file is entered to the application as a application parameter. This path has to be related to the path from which you are running the application. Paths to CSV files are provided in the main configuration file and have to be related to the main configuration file directory.

A structure of the configuration file is described [in the mentioned example](config/example-fifa-drawer.cfg).

The participants CSV file requires three columns:

1.  `USERNAME` - a name of a participant
2.  `IS ACTIVE INDICATOR` - indicates if the participant is active or not (possible values: `YES` or `NO`)
3.  `EMAIL ADDRESS` - an email address of the participant

The teams CSV file requires four columns:

1.  `NAME` - a name of a team
2.  `LEAGUE` - a name of a league in which the team is plaing
3.  `COUNTRY` - a name of a country from which the team comes
4.  `RANK` - a rank of the team (possible values: `0.5`, `1.0`, `1.5`, `2.0`, `2.5`, `3.0`, `3.5`, `4.0`, `4.5`, `5.0`)

The application always skips a first line (header) of both CSV files.

### Other Requirements
The application requires Java 1.7 to run. It can be found somewhere on [oracle.com](http://oracle.com).

Usage
-----
Command to run FIFA Drawer:

    java -jar FIFADrawer.jar path/to/the/main/configuration/file
    
Example
------------------
The result of a single draw should looks similar to below:

    10:30: Eddard + Catelyn vs. Robb + Jon
    10:45: Arya + Sansa vs. Rickon + Brandon

    Rank: 5.0

    1st TEAM: FC Bayern Munich (München) (Niemcy, Bundesliga)
    2nd TEAM: Manchester United F.C. (Anglia, Barclays Premier League)
