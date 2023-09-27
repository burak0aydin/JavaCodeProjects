
/**
 * Burak Aydın
 * 20190808047
 * 2023-04-06
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ex4_20190808047 {

}

class Computer {
    protected CPU cpu;
    protected RAM ram;

    public Computer(CPU cpu, RAM ram) {
        this.cpu = cpu;
        this.ram = ram;
    }

    public void run() {
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            sum += ram.getValue(i, i);
        }
        ram.setValue(0, 0, cpu.compute(sum, 0));
    }

    @Override
    public String toString() {
        return "Computer: " + cpu + " " + ram;
    }
}

class Laptop extends Computer {
    private int milliAmp;
    private int battery;

    public Laptop(CPU cpu, RAM ram, int milliAmp) {
        super(cpu, ram);
        this.milliAmp = milliAmp;
        this.battery = milliAmp * 30 / 100;
    }

    public int batteryPercentage() {
        return battery * 100 / milliAmp;
    }

    public void charge() {
        while (battery < milliAmp * 90 / 100) {
            battery += milliAmp * 2 / 100;
            if (battery > milliAmp * 90 / 100) {
                battery = milliAmp * 90 / 100;
            }
        }
    }

    @Override
    public void run() {
        if (batteryPercentage() > 5) {
            super.run();
            battery -= milliAmp * 3 / 100;
        } else {
            charge();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + battery;
    }
}

class Desktop extends Computer {
    private ArrayList<String> peripherals;

    public Desktop(CPU cpu, RAM ram, String... peripherals) {
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }

    public void plugIn(String peripheral) {
        peripherals.add(peripheral);
    }

    public String plugOut() {
        return peripherals.remove(peripherals.size() - 1);
    }

    public String plugOut(int index) {
        return peripherals.remove(index);
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            sum += ram.getValue(i, i);
        }
        cpu.compute(sum, sum);
        ram.setValue(0, 0, cpu.getResult());
    }

    @Override
    public String toString() {
        return super.toString() + " " + String.join(" ", peripherals);
    }
}

class CPU {
    private String name;
    private double clock;
    private int result;

    public CPU(String name, double clock) {
        this.name = name;
        this.clock = clock;
    }

    public String getName() {
        return name;
    }

    public double getClock() {
        return clock;
    }

    public int getResult() {
        return result;
    }

    public int compute(int a, int b) {
        result = a + b;
        return result;
    }

    @Override
    public String toString() {
        return "CPU: " + name + " " + clock + "Ghz";
    }
}

class RAM {
    private String type;
    private int capacity;
    private int[][] memory;

    public RAM(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        initMemory();
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    private void initMemory() {
        Random rand = new Random();
        memory = new int[capacity][capacity];
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                memory[i][j] = rand.nextInt(11);
            }
        }
    }

    private boolean check(int i, int j) {
        return i >= 0 && i < capacity && j >= 0 && j < capacity;
    }

    public int getValue(int i, int j) {
        if (!check(i, j)) {
            return -1;
        }
        return memory[i][j];
    }

    public void setValue(int i, int j, int value) {
        if (check(i, j)) {
            memory[i][j] = value;
        }
    }

    public String toString() {
        return "RAM: " + type + " " + capacity + "GB";
    }
}
