package com;

import java.util.Map;

public class StateMachineTemplate {
    protected static String nl;

    public static synchronized StateMachineTemplate create(String lineSeparator) {
        nl = lineSeparator;
        StateMachineTemplate result = new StateMachineTemplate();
        nl = null;
        return result;
    }

    public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
    protected final String TEXT_1 = "state = ";
    protected final String TEXT_2 = ";" + NL + "void handleEvent(Event evt) {" + NL + "\tswitch (state) {" + NL + "\tcase ";
    protected final String TEXT_3 = ":" + NL + "\t\tif (evt.id == ";
    protected final String TEXT_4 = ") {" + NL + "\t\t\tstate = ";
    protected final String TEXT_5 = ";" + NL + "\t\t\t";
    protected final String TEXT_6 = ";" + NL + "\t\t}" + NL + "\t\tbreak;" + NL + "\tdefault ";
    protected final String TEXT_7 = ";" + NL + "\t\t}" + NL + "\t\tbreak;" + NL + "\t}" + NL + "}";
    protected final String TEXT_8 = NL;

    public String generate(Object argument) {
        final StringBuffer stringBuffer = new StringBuffer();
        Map<?, ?> arguments = (Map<?, ?>) argument;
        stringBuffer.append(TEXT_1);
        stringBuffer.append(arguments.get("state_1"));
        stringBuffer.append(TEXT_2);
        stringBuffer.append(arguments.get("state_1"));
        stringBuffer.append(TEXT_3);
        stringBuffer.append(arguments.get("condition"));
        stringBuffer.append(TEXT_4);
        stringBuffer.append(arguments.get("state_2"));
        stringBuffer.append(TEXT_5);
        stringBuffer.append(arguments.get("operation"));
        stringBuffer.append(TEXT_6);
        stringBuffer.append(arguments.get("state_2"));
        stringBuffer.append(TEXT_3);
        stringBuffer.append(arguments.get("condition"));
        stringBuffer.append(TEXT_4);
        stringBuffer.append(arguments.get("state_1"));
        stringBuffer.append(TEXT_5);
        stringBuffer.append(arguments.get("operation"));
        stringBuffer.append(TEXT_7);
        stringBuffer.append(TEXT_8);
        return stringBuffer.toString();
    }
}