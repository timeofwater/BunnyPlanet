import java.util.concurrent.TimeUnit;

/**
 * @author Shang Zemo on 2022/10/18
 */
public class BattleGround {

    private Planet planet_0;
    private Planet planet_1;
    private Planet planet_2;

    private Planet planet_3;
    private Planet planet_4;
    private Planet planet_5;
    private Planet planet_6;

    private Planet planet_7;
    private Planet planet_8;
    private Planet planet_9;

    private Planet[] planets;

    private boolean[][] table;

    boolean isCleared;

    BattleGround() {
        init();
        checkWinner();
    }

    private void init() {
        planet_0 = new Planet(0, -1, 10);
        planet_1 = new Planet(1, -1, 0);
        planet_2 = new Planet(2, -1, 10);

        planet_3 = new Planet(3, 10);
        planet_4 = new Planet(4, 10);
        planet_5 = new Planet(5, 10);
        planet_6 = new Planet(6, 10);

        planet_7 = new Planet(7, 1, 10);
        planet_8 = new Planet(8, 1, 0);
        planet_9 = new Planet(9, 1, 10);

        planets = new Planet[]{planet_0, planet_1, planet_2, planet_3, planet_4, planet_5, planet_6, planet_7, planet_8, planet_9};

        table = new boolean[10][];
        for (int i = 0; i < table.length; i++) {
            table[i] = new boolean[10];
        }

        table[0][1] = true;
        table[1][0] = true;
        table[1][2] = true;
        table[1][3] = true;
        table[1][5] = true;
        table[2][1] = true;
        table[3][1] = true;
        table[3][5] = true;
        table[4][1] = true;
        table[4][6] = true;
        table[5][3] = true;
        table[5][8] = true;
        table[6][4] = true;
        table[6][8] = true;
        table[7][8] = true;
        table[8][5] = true;
        table[8][6] = true;
        table[8][7] = true;
        table[8][9] = true;
        table[9][8] = true;

        isCleared = false;
    }

    private void checkWinner() {
        Thread checker = new Thread(() -> {
            while (!this.isCleared) {
                int left = 0;
                int right = 0;
                for (Planet p :
                        planets) {
                    if (p.getCamp() > 0) {
                        right += 1;
                        if (left > 0) {
                            break;
                        }
                    } else if (p.getCamp() < 0) {
                        left += 1;
                        if (right > 0) {
                            break;
                        }
                    }
                }
                if (left * right == 0) {
                    isCleared = true;
                }
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "checker");
        checker.start();
    }

    public String buildCommand(int player, int index, int num, int form) {
        if (player * planets[index].getCamp() > 0) {
            return build(index, num, form);
        } else {
            return "it's not under your control.";
        }
    }

    private String build(int index, int num, int form) {
        return planets[index].addMember(num, form);
    }

    public String transferCommand(int player, int from, int to, int num) {
        if (player * planets[from].getCamp() > 0) {
            return transfer(from, to, num);
        } else {
            return "it's not under your control.";
        }
    }

    private String transfer(int from, int to, int num) {
        if (num <= 0) {
            return "illegal num(" + num + ").";
        } else {
            if (planets[from].getSoldier() >= num) {
                new Thread(() -> {
                    Planet p1 = planets[from];
                    Planet p2 = planets[to];
                    if (p1.sendSoldier(num)) {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        p2.enterSoldier(num, p1.getCamp());
                    }
                }, "transfer").start();
                return "true";
            } else {
                return "soldier is not enough(" + planets[from].getSoldier() + ").";
            }
        }
    }

    public String getPlanetsInfo(int index) {
        Planet planet = planets[index];
        return "planet " + index + " info:" +
                "\n  energy = " + planet.getEnergy() +
                "\n  camp number = " + planet.getCamp() +
                "\n  producer number = " + planet.getProducer() +
                "\n  soldier number = " + planet.getSoldier();
    }
}
