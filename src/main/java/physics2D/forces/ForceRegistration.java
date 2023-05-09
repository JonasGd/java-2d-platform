package physics2D.forces;

import physics2D.rigidbody.Rigidbody2D;

public class ForceRegistration {
    public ForceGenerator fg;
    public Rigidbody2D rb;

    public ForceRegistration(ForceGenerator fb, Rigidbody2D rb) {
        this.fg = fb;
        this.rb = rb;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != ForceRegistration.class) return false;

        ForceRegistration fr = (ForceRegistration) other;
        return fr.rb == this.rb && fr.fg == this.fg;
    }
}
