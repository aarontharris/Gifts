package com.ath.gifts.tools;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

public class Urls {
    private static final Charset UTF_8 = Charset.forName( "UTF-8" );

    /**
     * Assembles the paramString in URL form<br>
     * Keys are printed in order of natural ordering unless a TreeMap with a specific Comparator is used.
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String toUrlString( @NonNull Map<String, String> params ) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();

        TreeMap<String, String> sortedParams = null;
        if ( params instanceof TreeMap ) {
            sortedParams = (TreeMap<String, String>) params;
        } else {
            sortedParams = new TreeMap<>();
            sortedParams.putAll( params );
        }

        boolean first = true;
        for ( String key : sortedParams.keySet() ) {
            if ( first ) {
                first = false;
            } else {
                stringBuilder.append( "&" );
            }
            String encodedKey = URLEncoder.encode( key, UTF_8.name() );
            String encodedVal = URLEncoder.encode( params.get( key ), UTF_8.name() );
            stringBuilder.append( encodedKey ).append( "=" ).append( encodedVal );
        }
        return stringBuilder.toString();
    }

    private Map<String, String> params = null;
    private StringBuilder base = new StringBuilder();

    public Urls() {
    }

    public String toUrlString() throws UnsupportedEncodingException {
        String paramString = "";
        String base = this.base.toString();
        if ( params != null ) {
            String qmark = "?";
            if ( base.contains( "?" ) ) {
                qmark = "";
            }
            paramString = qmark + toUrlString( params );
        }
        return base + paramString;
    }

    @Override public String toString() {
        try {
            return toUrlString();
        } catch ( Exception e ) {
            return super.toString();
        }
    }

    /**
     * Preceeds parameters
     */
    public Urls append( String url ) {
        base.append( url );
        return this;
    }

    public Urls addParam( String key, String value ) {
        if ( params == null ) {
            params = new TreeMap<>();
        }
        params.put( key, value );
        return this;
    }

    public Urls addParam( String key, Integer value ) {
        return addParam( key, String.valueOf( value ) );
    }

    public Urls addParam( String key, Float value ) {
        return addParam( key, String.valueOf( value ) );
    }

    public Urls addParam( String key, Double value ) {
        return addParam( key, String.valueOf( value ) );
    }

    public Urls addParam( String key, Boolean value ) {
        return addParam( key, String.valueOf( value ) );
    }

}
