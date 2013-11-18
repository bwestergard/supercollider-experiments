(
v = Env.adsr;
a = JSCWindow( "envelope", Rect( 200, 450, 250, 114 )).front;
b = JSCEnvelopeView( a, Rect( 4, 4, 242, 80 ))
        .resize_( 5 )
        .action_({ arg b; b.editEnv( v, 0.0, 1.0 )})
        .lockBounds_( true )
        .horizontalEditMode_( \relay )
        .setEnv( v, 0.0, 1.0 );
JSCButton( a, Rect( 4, 88, 90, 24 ))
        .resize_( 7 )
        .states_([[ "Plot" ]])
        .action_({
                GUI.useID( \swing, { v.plot });
        });
a.front;
)

v.plot;

(
//use shift-click to keep a node selected
a = Window("envelope", Rect(200 , 450, 250, 100));

a.view.decorator =  FlowLayout(a.view.bounds);


b = EnvelopeView(a, Rect(0, 0, 230, 80))
.drawLines_(true)
.selectionColor_(Color.red)
.drawRects_(true)
.resize_(5)
.action_({arg b; [b.index,b.value].postln})
.thumbSize_(5)
.value_([[0.0, 0.1, 0.5, 1.0,0],[0.1,1.0,0.8,0.0,0]]);
a.front;

)

x = SynthDef(\changeEnv, { | out, t_gate = 0 , levels =#[0,0,0,0], times = #[0.1,1,0.1]| 
var z;
z = EnvGen.kr(Env(levels, times), t_gate) * MoogLadder.ar(VarSaw.ar((40 + LFNoise2.ar(30).range(1/8,-1/8)).midicps, 0, 0.1, width: 0.4), MouseX.kr(20,20000), 0.8).dup;
Out.ar(out, z)
}).play;

x.set(\t_gate, 0.1)

x.set(\levels, [0, 1, 0.8, 0]);
x.set(\times, [1/100, 1/10, 1/8]);

x.set(\t_gate, 1);

(
{
x.set(\levels, [0, 0, 0, 0], \t_gate, 1);
1.wait;
x.set(\levels, [0, 0, 1, 1]);

}.fork;
)

Help.gui;


