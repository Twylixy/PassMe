package space.staypony.passme.Enums;

public enum LoginMode
{
    STRICT {
        @Override
        public String toString() {
            return "strict";
        }
    },

    STRONG {
        @Override
        public String toString() {
            return "strong";
        }
    },

    PASSIVE {
        @Override
        public String toString() {
            return "passive";
        }
    }
}
