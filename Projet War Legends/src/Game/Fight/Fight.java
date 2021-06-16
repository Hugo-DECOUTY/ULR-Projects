package Game.Fight;

import Game.Team.Team;
import Hero.AbstractHero.Hero;
import Hero.AbstractHero.Placement;
import Hero.Sets.BasicSet.Archer;
import Passives.AbstractPassif.Passif;
import Passives.AbstractPassif.PassifType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import java.lang.reflect.*;
import java.util.Scanner;
import java.util.regex.*;

public class Fight {

    private boolean fightFinished;
    private Team teamPlayer1;
    private Team teamPlayer2;

    private static final int ACTUAL_TURN_COUNTER_TEAM_1 = 0;
    private static final int ACTUAL_TURN_COUNTER_TEAM_2 = 0;


    public Fight() {
        this.fightFinished = false;
        this.teamPlayer1 = null;
        this.teamPlayer2 = null;
    }

    public void triggerPassifAction(Team team, String roundState){

        Hero warchief = team.getWarChief();
        Passif passif = warchief.getPassif();
        PassifType type = passif.getType();

        if(type == PassifType.StartOfTheGame && roundState.equals("StartOfGame")){
            if(team == this.getTeamPlayer1()){
                passif.use(warchief, this.getTeamPlayer1(), this.getTeamPlayer2());
            } else {
                passif.use(warchief, this.getTeamPlayer2(), this.getTeamPlayer1());
            }
        } else {

            boolean StartRound = type == PassifType.EachStartTurn && (roundState.equals("StartRoundTeam1") || roundState.equals("StartRoundTeam2"));
            boolean EndRound = type == PassifType.EachEndTurn && (roundState.equals("EndRoundTeam1") || roundState.equals("EndRoundTeam2"));

            if(team == this.getTeamPlayer1()){

                if(
                        type == PassifType.EachStartAllyTurn && roundState.equals("StartRoundTeam1") ||
                        type == PassifType.EachStartEnemyTurn && roundState.equals("StartRoundTeam2") ||
                        type == PassifType.EachEndAllyTurn && roundState.equals("EndRoundTeam1") ||
                        type == PassifType.EachEndEnemyTurn && roundState.equals("EndRoundTeam2") ||
                        StartRound || EndRound

                ){
                    passif.use(warchief, this.getTeamPlayer1(), this.getTeamPlayer2());
                }

            } else {

                if(
                        type == PassifType.EachStartAllyTurn && roundState.equals("StartRoundTeam2") ||
                        type == PassifType.EachStartEnemyTurn && roundState.equals("StartRoundTeam1") ||
                        type == PassifType.EachEndAllyTurn && roundState.equals("EndRoundTeam2") ||
                        type == PassifType.EachEndEnemyTurn && roundState.equals("EndRoundTeam1") ||
                        StartRound || EndRound
                ){
                    passif.use(warchief, this.getTeamPlayer2(), this.getTeamPlayer1());
                }

            }
        }
    }

    /**
     * Méthode permettant à un joueur de créer son équipe
     */
    public Team createTeam() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);

        ArrayList<Hero> choice = heroesChoice(sc);

        Hero warChief = defineWarChief(choice, sc);

        int nbVanGuard = choseNbVanGuard(sc);

        defineVanGuard(choice, nbVanGuard, sc);

        return new Team(warChief, choice, 3);
    }

    public ArrayList<Hero> heroesChoice(Scanner sc) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        int nbChoice = 0;
        Hero chosen;

        ArrayList<Hero> heroList = initialisationHeros();

        ArrayList<Hero> choice = new ArrayList<>();

        while(choice.size()<3){
            System.out.println("Choisir un héros");
            for(int i=0; i<heroList.size(); i++){
                heroList.get(i).display(i);
            }
            nbChoice = sc.nextInt()-1;
            if(nbChoice<heroList.size()){
                chosen =  heroList.get(nbChoice);
                choice.add(chosen);
                heroList.remove(chosen);
            }else{//à enlever plus tard
                System.out.println("nombre entré non valide");
            }
        }
        return choice;
    }

    public Hero defineWarChief(ArrayList<Hero> heroes, Scanner sc){
        int nbChoice = 0;
        Hero warChief = null;
        boolean choiceChief = false;

        while (!choiceChief){
            System.out.println("Choisir un chef de guerre");
            for(int i=0; i<heroes.size(); i++){
                heroes.get(i).display(i);
            }

            nbChoice = sc.nextInt()-1;

            if(nbChoice<heroes.size()){
                warChief = heroes.get(nbChoice);
                choiceChief = true;
            }else{//à enlever plus tard
                System.out.println("nombre entré non valide");
            }
        }
        return warChief;
    }

    public int choseNbVanGuard(Scanner sc){
        int nbChoice = 0;
        boolean validNb = false;

        while(!validNb){
            System.out.println("choisissez le nombre de héros pour votre avant garde");
            nbChoice = sc.nextInt();
            if(nbChoice == 2 || nbChoice == 1){
                validNb = true;
            }else{//à enlever plus tard
                System.out.println("nombre entré non valide");
            }
        }
        return nbChoice;
    }

    public void defineVanGuard(ArrayList<Hero> heroes, int nbVanGuard, Scanner sc){

        int nbChoice = 0;
        boolean placement = false;
        int nbVanGuardWanted = nbChoice;

        while(nbVanGuard!=nbVanGuardWanted){

            if(nbVanGuard==1) {
                System.out.println("Choisissez un héros pour votre avant-garde");
            }else if (nbVanGuard==2){
                System.out.println("Choisissez un second héros pour votre avant-garde");
            }

            for(int i=0; i<heroes.size(); i++){
                heroes.get(i).display(i);
            }

            nbChoice = sc.nextInt();

            if(nbChoice<heroes.size()){
                nbVanGuard++;
                heroes.get(nbChoice-1).setPlacement(Placement.VanGuard);
            }else{//à enlever plus tard
                System.out.println("nombre entré non valide");
            }
        }
    }

    /**
     * Renvoie l'équipe du héros qui attaque.
     * @param attacker : L'attaquant
     * @return l'équipe de l'attaquant.
     */
    public Team returnsTeamOfTheAttacker(Hero attacker){
        return this.getTeamPlayer1().getTeam().contains(attacker) ? this.getTeamPlayer1() : this.getTeamPlayer2();
    }

    /**
     * Méthode principale qui s'occupe du déroulement du jeu dans son intégralité
     * @return int, retourne le numéro de l'équipe qui à gagnée le combat
     */
    public int startGame() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        int nb_tours = 0;

        //Création des équipes
        this.setTeamPlayer1(createTeam());
        this.setTeamPlayer2(createTeam());
        Team teamPlayer1 = this.getTeamPlayer1();
        Team teamPlayer2 = this.getTeamPlayer2();

        //TODO : Vérifier les passifs des deux héros de chaque équipe (Start of Game)
        this.triggerPassifAction(teamPlayer1, "StartOfGame");
        this.triggerPassifAction(teamPlayer2, "StartOfGame");

        //Début de la partie : Tant qu'une des deux équipes est vivante...
        while(teamPlayer1.checkTeamAlive() || teamPlayer2.checkTeamAlive()){

            //Incrémentation du nombre de tours
            nb_tours++;

            //Baisse du cooldown de tous les héros jusqu'à ce que l'un d'entre eux atteigne 0.
            Hero attacker = this.searchHeroWithLesserCooldown();
            Team attackerTeam = this.returnsTeamOfTheAttacker(attacker);
            Team enemyTeam = attackerTeam == teamPlayer1 ? teamPlayer1 : teamPlayer2;
            int attackerTeamNumber = attackerTeam == teamPlayer1 ? 1 : 2;

            //En fonction de l'attaquant, afficher l'équipe attaquante
            if(attackerTeam == this.getTeamPlayer1()){
                System.out.println("C'est à l'équipe 1 d'attaquer avec " + attacker.getName());
            } else {
                System.out.println("C'est à l'équipe 2 d'attaquer avec " + attacker.getName());
            }

            //TODO : Compétences et vérification d'utilisation de passifs à tous les instants du tour
            this.triggerPassifAction(attackerTeam, "StartRoundTeam" + attackerTeamNumber);

            //TODO : Gestion des dégâts après utilisation des compétences
            //TODO : Affichage de la compétence choisie et de la/des cible(s) à choisir : Méthode appelant skillType.
            int SkillChoice = 0;
            Hero singleTarget = null;

            attacker.getSkill(SkillChoice).useSkillModel(attacker, singleTarget, attackerTeam, enemyTeam);

            this.triggerPassifAction(attackerTeam, "EndRoundTeam" + attackerTeamNumber);
        }
        return this.getTeamPlayer1().checkTeamAlive() ? 1 : 2;
    }

    /**
     * Récupère le héros qui a la vitesse d'attaque la plus faible pour la mettre à 0, lui permettant d'attaquer.
     * NOTE : En cas d'égalité sur qui attaque en premier, ce seront les compétences qui seront en charge de modifier
     * la vitesse d'attaque.
     * @return Hero : le héros qui attaquera en premier.
     */
    public Hero searchHeroWithLesserCooldown(){
        Hero hero_team1, hero_team2;
        //int cooldown_unit_counter = 0;

        do{

            hero_team1 = this.getTeamPlayer1().decrementCooldownTeam();
            hero_team2 = this.getTeamPlayer2().decrementCooldownTeam();
            //cooldown_unit_counter++;

        }while(hero_team1 != null && hero_team2 != null);

        return hero_team1 != null ? hero_team1 : hero_team2;
    }

    /**
     * Méthode instancie une héros de chaque type pour le mettre dans une ArrayList
     * @return ArrayList<Hero>
     */
    public ArrayList<Hero> initialisationHeros() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Pattern pattern;
        Matcher matcher;
        Package[] packages = Package.getPackages();
        ArrayList<Hero> heros = new ArrayList<>();
        Class[] classes = null;

        for(Package p : packages) {
            System.out.println(p.getName());
            pattern = Pattern.compile("Hero.Sets.Set%");
            matcher = pattern.matcher(p.getName());
            if (matcher.find()) {
                classes = getClasses(p.getName());
                for (Class c : classes) {
                    heros.add((Hero) c.getDeclaredConstructor().newInstance());
                }
            }
        }


        return heros;
    }


    public static Class[] getClasses(String pckgname) throws ClassNotFoundException {
        ArrayList<Class> classes = new ArrayList<Class>();
        // Get a File object for the package
        File directory = null;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = pckgname.replace('.', '/');
            URL resource = cld.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("No resource for " + path);
            }
            directory = new File(resource.getFile());
        }
        catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package");
        }
        if (directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                // we are only interested in .class files
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(pckgname + '.' + files[i].substring(0, files[i].length() - 6)));
                }
            }
        }
        else {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
        }
        Class[] classesA = new Class[classes.size()];
        classes.toArray(classesA);
        return classesA;
    }

    public boolean isFightFinished() {
        return fightFinished;
    }

    public void setFightFinished(boolean fightFinished) {
        this.fightFinished = fightFinished;
    }

    public Team getTeamPlayer1() {
        return teamPlayer1;
    }

    public void setTeamPlayer1(Team teamPlayer1) {
        this.teamPlayer1 = teamPlayer1;
    }

    public Team getTeamPlayer2() {
        return teamPlayer2;
    }

    public void setTeamPlayer2(Team teamPlayer2) {
        this.teamPlayer2 = teamPlayer2;
    }

}
