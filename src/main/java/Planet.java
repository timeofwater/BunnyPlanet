import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Shang Zemo on 2022/10/18
 */
public class Planet {

    private final int index;
    private int camp;
    private int energy;
    private int producer;
    private int soldier;

    Planet(int index, int energy) {
        this(index, 0, energy);
    }

    Planet(int index, int camp, int energy) {
        this.index = index;
        this.camp = camp;
        this.energy = energy;
        this.producer = 0;
        this.soldier = 0;

        grow();
    }

    private void grow() {
        Thread grow = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.energy += this.producer;
            }
        }, "planet " + this.index + "'s grower");
        grow.start();
    }

    String addProducer() {
        return addProducer(1);
    }

    String addProducer(int num) {
        return addMember(num, 1);
    }

    String addSoldier() {
        return addSoldier(1);
    }

    String addSoldier(int num) {
        return addMember(num, 2);
    }

    String addMember(int num, int form) {
        if (num <= 0) {
            return "illegal num(" + num + ").";
        }

        if (num * 10 < energy) {
            return "energy is not enough(" + energy + ").";
        } else {
            energy -= num * 10;
            switch (form) {
                case 1:
                    producer += num;
                    return " " + this.index + "|p: " + (producer - num) + " -> " + producer;
                case 2:
                    soldier += num;
                    return " " + this.index + "|s: " + (producer - num) + " -> " + producer;
                default:
                    return "illegal form.";
            }
        }
    }

    boolean enterSoldier(int num, int camp) {
        if (num <= 0) {
            return false;
        } else {
            if (camp * this.camp <= 0) {
                soldier -= num;
                if (soldier < 0) {
                    soldier = 0;
                    energy = 0;
                    if (producer == 0) {
                        producer = 1;
                    }
                    this.camp = camp;
                }
            } else {
                soldier += num;
            }
            return true;
        }
    }

    boolean sendSoldier(int num) {
        if (num <= 0) {
            return false;
        } else {
            if (this.soldier < num) {
                return false;
            } else {
                this.soldier -= num;
                return true;
            }
        }
    }

    public int getCamp() {
        return camp;
    }

    public int getEnergy() {
        return energy;
    }

    public int getIndex() {
        return index;
    }

    public int getProducer() {
        return producer;
    }

    public int getSoldier() {
        return soldier;
    }
}
