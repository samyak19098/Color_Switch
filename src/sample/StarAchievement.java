package sample;

public class StarAchievement extends Achievement {
    public long RequiredStars;

    public StarAchievement(long rs){
        super(); RequiredStars=rs;
        text.setText("Collect "+RequiredStars+" stars");
    }

    @Override
    public    boolean Requirement(long OwnedStars){
        if(OwnedStars>=RequiredStars)
            return true;
        return false;
    }
}
