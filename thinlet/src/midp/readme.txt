To make a MIDP version of Thinlet you have to compile the Converter file in this
directory and run it (javac Converter.java; java Converter). Now you have the
thinlet\midp\Thinlet.java file.

Two simple demo application is included a calculator and a widget demo, to
compile and run it you need the J2ME Wireless Toolkit. Do the followings:

- create a new project (e.g. ThinletDemo)
- copy MANIFEST.MF and ThinletDemo.jad to \WTK104\apps\ThinletDemo\bin
- copy Thinlet.java to \WTK104\apps\ThinletDemo\src\thinlet\midp
- copy calculator\Calculator.java to \WTK104\apps\ThinletDemo\src\thinlet\midp\calculator
- copy demo\Demo.java to \WTK104\apps\ThinletDemo\src\thinlet\midp\demo
- copy ThinletDemo.png \WTK104\apps\ThinletDemo\res
- copy calculator\calculator.xml to \WTK104\apps\ThinletDemo\res\thinlet\midp\calculator
- copy demo\demo.xml and demo\image.png to \WTK104\apps\ThinletDemo\res\thinlet\midp\demo

The MIDP version is very similar to the J2SE version, the only difference is the
event handling (because there is no reflection), you have to overwrite Thinlet's

  protected void handle(Object source, String action)

method.