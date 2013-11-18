(
~swingify = Prout({ |ev|
        var started = thisThread.beats,
        now, adjust;
        while { ev.notNil } {
                now = thisThread.beats - started;
                // an odd number here means we're on an off-beat
                if((now / ev[\swingBase]).round.asInteger.odd) {
                        adjust = ev[\swingBase] * ev[\swingAmount];
                        ev[\timingOffset] = (ev[\timingOffset] ? 0) + adjust;
                        ev[\sustain] = ev.use { ~sustain.value } - adjust;
                } {
                        // will the next note swing?
                        if(((now + ev.delta) / ev[\swingBase]).round.asInteger.odd) {
                                // if yes, then this note needs to be longer
                                ev[\sustain] = ev.use { ~sustain.value } + (ev[\swingBase] *
ev[\swingAmount]);
                        };
                };
                ev = ev.yield;
        };
});
)
