
/**
 * Burak Aydın
 * 20190808047
 * 2023-05-14
 */
import java.util.*;

public class Ex7_20190808047 {
}

// INTERFACES

interface Damageable {
    void takeDamage(int damage);

    void takeHealing(int healing);

    boolean isAlive();
}

interface Caster {
    void castSpell(Damageable target);

    void learnSpell(Spell spell);
}

interface Combat extends Damageable {
    void attack(Damageable target);

    void lootWeapon(Weapon weapon);
}

interface Useable {
    int use();
}

// EXCEPTİONS

class PartyLimitReachedException extends Exception {
    public PartyLimitReachedException(String message) {
        super(message);
    }
}

class AlreadyInPartyException extends Exception {
    public AlreadyInPartyException(String message) {
        super(message);
    }
}

class CharacterIsNotInPartyException extends Exception {
    public CharacterIsNotInPartyException(String message) {
        super(message);
    }
}

// CLASSES

class Spell implements Useable {
    private int minHeal;
    private int maxHeal;
    private String name;

    public Spell(String name, int minHeal, int maxHeal) {
        this.name = name;
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int heal() {
        return minHeal + (int) (Math.random() * (maxHeal - minHeal + 1));
    }

    public int use() {
        return heal();
    }
}

class Weapon implements Useable {
    private int minDamage;
    private int maxDamage;
    private String name;

    public Weapon(String name, int minDamage, int maxDamage) {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int attack() {
        Random random = new Random();
        return random.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    public int use() {
        return attack();
    }
}

class Attributes {
    private int strength;
    private int intelligence;

    public Attributes() {
        this.strength = 3;
        this.intelligence = 3;
    }

    public Attributes(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void increaseStrength(int amount) {
        strength += amount;
    }

    public void increaseIntelligence(int amount) {
        intelligence += amount;
    }

    public String toString() {
        return "Attributes [Strength=" + strength + ", intelligence=" + intelligence + "]";
    }
}

abstract class Character implements Comparable<Character> {
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    public Character(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
        this.level = 1;
        this.health = 100;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public abstract void levelUp();

    @Override
    public int compareTo(Character other) {
        return Integer.compare(level, other.level);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " LvL: " + level + " - " + attributes;
    }
}

abstract class PlayableCharacter extends Character implements Damageable {
    private boolean inParty;
    private Party party;

    public PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
        this.inParty = false;
        this.party = null;
    }

    public boolean isInParty() {
        return inParty;
    }

    public void joinParty(Party party) {
        if (inParty) {
            System.out.println(getName() + " is already in a party.");
        } else {
            try {
                party.addCharacter(this);
                inParty = true;
                this.party = party;
            } catch (PartyLimitReachedException e) {
                System.out.println(e.getMessage());
            } catch (AlreadyInPartyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void quitParty() {
        if (inParty) {
            try {
                party.removeCharacter(this);
                inParty = false;
                party = null;
            } catch (CharacterIsNotInPartyException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(getName() + " is not in any party.");
        }
    }
}

abstract class NonPlayableCharacter extends Character {

    public NonPlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
}

class Merchant extends NonPlayableCharacter {

    public Merchant(String name) {
        super(name, new Attributes(0, 0));
    }

    @Override
    public void levelUp() {
    }
}

class Skeleton extends NonPlayableCharacter implements Combat {

    public Skeleton(String name, Attributes attributes) {
        super(name, attributes);
    }

    @Override
    public void lootWeapon(Weapon weapon) {
    }

    @Override
    public void levelUp() {
        level++;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(1);
    }

    @Override
    public void takeHealing(int healing) {
        takeDamage(healing);
    }

    @Override
    public int compareTo(Character other) {
        return super.compareTo(other);
    }

    @Override
    public int getLevel() {
        return super.getLevel();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void attack(Damageable target) {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void takeDamage(int damage) {
    }
}

class Warrior extends PlayableCharacter implements Combat {
    private Useable weapon;

    public Warrior(String name) {
        super(name, new Attributes(4, 2));
        this.health = 35;
    }

    public Useable getWeapon() {
        return weapon;
    }

    public void setWeapon(Useable weapon) {
        this.weapon = weapon;
    }

    @Override
    public void levelUp() {
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
        level++;
    }

    @Override
    public void attack(Damageable target) {
        if (weapon != null) {
            int damage = attributes.getStrength() + weapon.use();
            target.takeDamage(damage);
        }
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        setWeapon(weapon);
    }

    @Override
    public void takeDamage(int damage) {
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public void takeHealing(int healing) {
        throw new UnsupportedOperationException("Unimplemented method 'takeHealing'");
    }

    @Override
    public boolean isAlive() {
        throw new UnsupportedOperationException("Unimplemented method 'isAlive'");
    }

    @Override
    public boolean isInParty() {
        return super.isInParty();
    }

    @Override
    public void joinParty(Party party) {
        super.joinParty(party);
    }

    @Override
    public void quitParty() {
        super.quitParty();
    }

    @Override
    public int compareTo(Character other) {
        return super.compareTo(other);
    }

    @Override
    public int getLevel() {
        return super.getLevel();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Cleric extends PlayableCharacter implements Caster {
    private Useable spell;

    public Cleric(String name) {
        super(name, new Attributes(2, 4));
        this.health = 25;
    }

    public Useable getSpell() {
        return spell;
    }

    public void setSpell(Useable spell) {
        this.spell = spell;
    }

    @Override
    public void levelUp() {
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(2);
        level++;
    }

    @Override
    public void castSpell(Damageable target) {
        if (spell != null) {
            int healing = attributes.getIntelligence() + spell.use();
            target.takeHealing(healing);
        }
    }

    @Override
    public void learnSpell(Spell spell) {
        setSpell(spell);
    }

    @Override
    public boolean isInParty() {
        return super.isInParty();
    }

    @Override
    public void joinParty(Party party) {
        super.joinParty(party);
    }

    @Override
    public void quitParty() {
        super.quitParty();
    }

    @Override
    public int compareTo(Character other) {
        return super.compareTo(other);
    }

    @Override
    public int getLevel() {
        return super.getLevel();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void takeDamage(int damage) {
    }

    @Override
    public void takeHealing(int healing) {
    }
}

class Paladin extends PlayableCharacter implements Combat, Caster {
    private Useable weapon;
    private Useable spell;

    public Paladin(String name) {
        super(name, new Attributes());
        this.health = 30;
    }

    public Useable getWeapon() {
        return weapon;
    }

    public void setWeapon(Useable weapon) {
        this.weapon = weapon;
    }

    public Useable getSpell() {
        return spell;
    }

    public void setSpell(Useable spell) {
        this.spell = spell;
    }

    @Override
    public void levelUp() {
        attributes.increaseStrength(level % 2 == 0 ? 2 : 1);
        attributes.increaseIntelligence(level % 2 == 0 ? 1 : 2);
        level++;
    }

    @Override
    public void attack(Damageable target) {
        if (weapon != null) {
            int damage = attributes.getStrength() + weapon.use();
            target.takeDamage(damage);
        }
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        setWeapon(weapon);
    }

    @Override
    public void castSpell(Damageable target) {
        if (spell != null) {
            int healing = attributes.getIntelligence() + spell.use();
            target.takeHealing(healing);
        }
    }

    @Override
    public void learnSpell(Spell spell) {
        setSpell(spell);
    }

    @Override
    public boolean isInParty() {
        return super.isInParty();
    }

    @Override
    public void joinParty(Party party) {
        super.joinParty(party);
    }

    @Override
    public void quitParty() {
        super.quitParty();
    }

    @Override
    public int compareTo(Character other) {
        return super.compareTo(other);
    }

    @Override
    public int getLevel() {
        return super.getLevel();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void takeDamage(int damage) {
    }

    @Override
    public void takeHealing(int healing) {
    }
}

class Party {
    private static final int partyLimit = 8;
    private Collection<Combat> fighters;
    private Collection<Caster> healers;
    private int mixedCount;

    public Party() {
        fighters = new ArrayList<>();
        healers = new ArrayList<>();
        mixedCount = 0;
    }

    public void addCharacter(PlayableCharacter character) throws PartyLimitReachedException, AlreadyInPartyException {
        if (fighters.contains(character) || healers.contains(character)) {
            throw new AlreadyInPartyException("Character is already in the party.");
        }

        if (fighters.size() + healers.size() >= partyLimit) {
            throw new PartyLimitReachedException("Party limit has been reached.");
        }

        if (character instanceof Combat) {
            fighters.add((Combat) character);
        } else if (character instanceof Caster) {
            healers.add((Caster) character);
            if (character instanceof Paladin) {
                mixedCount++;
            }
        }
    }

    public void removeCharacter(PlayableCharacter character) throws CharacterIsNotInPartyException {
        if (!fighters.contains(character) && !healers.contains(character)) {
            throw new CharacterIsNotInPartyException("Character is not in the party.");
        }

        if (character instanceof Combat) {
            fighters.remove(character);
        } else if (character instanceof Caster) {
            healers.remove(character);
            if (character instanceof Paladin) {
                mixedCount--;
            }
        }
    }

    public void partyLevelUp() {
        for (Combat fighter : fighters) {
            ((Character) fighter).levelUp();
        }
        for (Caster healer : healers) {
            ((Character) healer).levelUp();
        }
    }

    @Override
    public String toString() {
        List<PlayableCharacter> allCharacters = new ArrayList<>();
        allCharacters.addAll((Collection<? extends PlayableCharacter>) fighters);
        allCharacters.addAll((Collection<? extends PlayableCharacter>) healers);

        Collections.sort(allCharacters, Comparator.comparingInt(PlayableCharacter::getLevel));

        StringBuilder sb = new StringBuilder();
        for (PlayableCharacter character : allCharacters) {
            sb.append(character).append("\n");
        }
        return sb.toString();
    }
}

class Barrel implements Damageable {
    private int health = 30;
    private final int capacity = 10;

    public void explode() {
        System.out.println("Explodes");
    }

    public void repair() {
        System.out.println("Repairing");
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            explode();
        }
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
        if (health > capacity) {
            health = capacity;
        }
        repair();
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}

class TrainingDummy implements Damageable {
    private int health = 25;

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}
