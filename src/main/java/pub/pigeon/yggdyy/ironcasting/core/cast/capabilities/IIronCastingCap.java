package pub.pigeon.yggdyy.ironcasting.core.cast.capabilities;

public interface IIronCastingCap {
    //the power factor from stack, ready to be read by iron spell
    double getPowerArgFactor();
    void setPowerArgFactor(double newValue);
    //how many times had this entity cast iron spell (by hex) in this tick? associated with media consumption
    int getIronSpellCount();
    void setIronSpellCount(int newValue);
}
