<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
      <head>
        <title>Thinlet Widget Documentation</title>
        <style type="text/css">
          h2 { font: bold 12pt Verdana,Arial,Helvetica,sans-serif; }
          h3 { font: bold 15pt Verdana,Arial,Helvetica,sans-serif; }

          body { background: #ffffff; font: 11pt Verdana,Arial,Helvetica,sans-serif;}
          td, input, textarea, caption, p {font: 9pt Verdana,Arial,Helvetica,sans-serif; }
          p { margin-top: 6pt; margin-bottom: 6pt; }
          td { vertical-align: top; }
          a:link, a:visited, a:active { color: #3399cc; text-decoration: none; }
          a:hover { color: #ff9933; text-decoration: underline; }

          .ht { background: #000000; color: #999999; vertical-align: middle; }
          .hm { background: #3399cc; vertical-align: bottom; }
          a.hn:link, a.hn:visited, a.hn:active { color: #000000; }
          a.hn:hover { color: #cccccc; background: #ff9933; text-decoration: none; }
          .hs { background: #000000; color: #cccccc; font-weight: bold; }
          .hb { background: #000000; }

          .mb { background: #ededed; }
          .md { background: #999999; }
          .mt { }
          .bt { color: #999999; text-align: right; }

	  .sec {color: #666666; margin-top: 10pt; font: bold 10pt Verdana,Arial,Helvetica,sans-serif; text-align: left;}
          .th, .to, .te, .tn, .tor, .ter, .tir { padding-left: 4pt; padding-right: 4pt;
          border-bottom: 1pt solid #cccccc; text-align: left; }
          .th, .tir { background: #ededed; color: #666666; font-weight: bold;
          border-top: 1pt solid #cccccc; border-bottom: 1pt solid #cccccc;}
          .te, .ter { background: #ededed; }
          .tn { background: #ededed; color: #666666; font-weight: bold; }
          .tc { color: #666666; font-weight: bold; text-align: left; }

          .tor, .ter { text-align: right; padding-right: 0pt; }
          .tir { text-align: center;  border-top: 1pt solid #999966;  border-bottom: 1pt solid #999966;}
          .code { background: #ededed; border-top: 1pt solid #cccccc; border-bottom: 1pt solid #cccccc; }
       
        </style>

      </head>
    
      <body >
        <table border="0" width="99%" cellspacing="0">
          <tr>
            <td class="tir" width="15%"> 
              <br />
               <a href="http://www.thinlet.com/">www.thinlet.com</a><br />
               <a href="http://thinlet.sourceforge.net/">thinlet.sourceforge.net</a>
             </td>
            <td class="tir">
              <h3 align="center">Thinlet Widgets</h3>
            </td>
          </tr>
        </table>
      
        <table border="0" height="80%" cellspacing="0" cellpadding="0"><tr>
            <td class="th"> 
              <br />
            </td>
            <td width="15%" class="th">
              <br />
              <br />
             &#187; <a href="button.xml" >button</a> <br />
             &#187;  <a href="checkbox.xml" >checkbox</a><br />
             &#187;  <a href="combobox.xml" >combobox</a><br />
             &#187; <a href="component.xml" >component</a><br />
             &#187;  <a href="desktop.xml" >desktop</a><br />
             &#187;  <a href="dialog.xml" >dialog</a><br />
             &#187; <a href="label.xml" >label</a><br />
             &#187; <a href="list.xml" >list</a><br />
             &#187; <a href="menubar.xml" >menubar</a><br />
             &#187; <a href="panel.xml" >panel</a><br />
             &#187; <a href="passwordfield.xml" >passwordfield</a><br />
             &#187; <a href="progressbar.xml" >progressbar</a><br />
             &#187; <a href="separator.xml" >separator</a><br />
             &#187; <a href="slider.xml" >slider</a><br />
             &#187; <a href="spinbox.xml" >spinbox</a><br />
             &#187; <a href="splitpane.xml" >splitpane</a><br />
             &#187; <a href="tabbedpane.xml" >tabbedpane</a><br />
             &#187; <a href="table.xml" >table</a><br />
             &#187; <a href="textarea.xml" >textarea</a><br />
             &#187; <a href="textfield.xml" >textfield</a><br />
             &#187; <a href="togglebutton.xml" >togglebutton</a><br />
             &#187; <a href="tree.xml">tree</a><br />
	     <br />
	     <br />
	     <a href="../api/index.html">Thinlet API</a>
           </td>
           <td width="20px"> </td>
           <td>
             <br />
              <xsl:apply-templates select="widget"/>
            </td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>

        
  <xsl:template match="widget">

        <h2><xsl:value-of select="@title"/></h2>
        <p><xsl:value-of select="p"/></p>
        <xsl:if test="xul">
          <p class="sec">Example:<br/></p>
          <table class="th" border="0" summary="Parameters" cellpadding="5" width="90%"><tr><td>
                <pre style="font: 11pt; color: #000000;"><xsl:value-of select="xul"/></pre>
              </td></tr></table>
        </xsl:if>
        <p class="sec">Parameters:<br/></p>
        <xsl:apply-templates select="parameters"/>
        <xsl:apply-templates select="widget"/>
        <xsl:apply-templates select="keys"/>
      
   
  </xsl:template>  <xsl:template match="parameters">
    <table border="0" summary="Parameters" cellpadding="1" width="100%">
     
      <tr class="th">
	
        <th width="10%">Name</th>
        <th width="10%">Type</th>
        <th width="10%">Default</th>
        <th >Description</th>
      </tr>
      <xsl:apply-templates select="parameter"/>
    </table>
 
  </xsl:template>  
  <xsl:template match="parameter">
    <tr>
	<xsl:attribute name="BGCOLOR">
   	  <xsl:choose>
 	    <xsl:when test="position() mod 2 = 1">#FFFFFF</xsl:when>
 	    <xsl:when test="position() mod 2 = 0">#EDEDED</xsl:when>
	  </xsl:choose>
	</xsl:attribute>
      <xsl:choose>
        <xsl:when test="@extend">
         
          <td colspan="4" class="code"><b>Extends <a><xsl:attribute name="href"><xsl:value-of select="@extend"/>.xml</xsl:attribute><xsl:value-of select="@extend"/></a></b>: <xsl:value-of select="."/></td>
        </xsl:when>
        <xsl:otherwise>
          <td><xsl:value-of select="@name"/></td>
          <td><xsl:value-of select="@type"/></td>
          <xsl:choose>
            <xsl:when test="@default">
              <td><xsl:value-of select="@default"/></td>
            </xsl:when>
            <xsl:otherwise>
              <td>none</td>
            </xsl:otherwise>
          </xsl:choose>
          <td><xsl:value-of select="."/></td>
        </xsl:otherwise>
      </xsl:choose>

     
     
    </tr>
    
  </xsl:template>
  <xsl:template match="keys">
    <p class="sec"><b>Keyboard actions</b></p>
     <table border="0" summary="Keys" cellpadding="1" width="100%">
     
      <tr class="th">
        <th>Operation</th>
        <th>Action</th>
     
      </tr>
      <xsl:apply-templates select="key"/>
    </table>
  </xsl:template>
  <xsl:template match="key">
    <tr>
	<xsl:attribute name="BGCOLOR">
   	  <xsl:choose>
 	    <xsl:when test="position() mod 2 = 1">#FFFFFF</xsl:when>
 	    <xsl:when test="position() mod 2 = 0">#EDEDED</xsl:when>
	  </xsl:choose>
	</xsl:attribute>
      <xsl:choose>
        <xsl:when test="@extend">
         
          <td colspan="2"><xsl:value-of select="."/></td>
        </xsl:when>
        <xsl:otherwise>
          <td><xsl:value-of select="@sequence"/></td>
          <td><xsl:value-of select="."/></td>
        </xsl:otherwise>
      </xsl:choose>
    
    </tr>
    
  </xsl:template>

</xsl:stylesheet>
