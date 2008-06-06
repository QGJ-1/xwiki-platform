/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.wikimodel.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.wikimodel.wem.IWemConstants;
import org.wikimodel.wem.IWemListener;
import org.wikimodel.wem.WikiFormat;
import org.wikimodel.wem.WikiParameter;
import org.wikimodel.wem.WikiParameters;
import org.xwiki.component.logging.AbstractLogEnabled;
import org.xwiki.rendering.block.AbstractBlock;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.BoldBlock;
import org.xwiki.rendering.block.BulletedListBlock;
import org.xwiki.rendering.block.DOM;
import org.xwiki.rendering.block.ItalicBlock;
import org.xwiki.rendering.block.ListBLock;
import org.xwiki.rendering.block.ListItemBlock;
import org.xwiki.rendering.block.MacroBlock;
import org.xwiki.rendering.block.NumberedListBlock;
import org.xwiki.rendering.block.ParagraphBlock;
import org.xwiki.rendering.block.SectionBlock;
import org.xwiki.rendering.block.SpaceBlock;
import org.xwiki.rendering.block.SpecialSymbolBlock;
import org.xwiki.rendering.block.WordBlock;
import org.xwiki.rendering.listener.Listener;
import org.xwiki.rendering.listener.SectionLevel;

public class DocumentGeneratorListener 
    extends AbstractLogEnabled implements IWemListener
{
    private Stack<Block> stack = new Stack<Block>();
    
    private final MarkerBlock marker = new MarkerBlock();
    
    private class MarkerBlock extends AbstractBlock
    {
        public void traverse(Listener listener)
        {
        }
    }
    
    public DOM getDocument()
    {
        return new DOM(generateListFromStack());
    }

    public void beginDefinitionDescription()
    {
    }

    public void beginDefinitionList(WikiParameters params)
    {
    }

    public void beginDefinitionTerm()
    {
    }

    public void beginDocument()
    {
    }

    /**
     * A format is a special formatting around an inline element, such as bold, italics, etc.
     */
    public void beginFormat(WikiFormat format)
    {
        this.stack.push(this.marker);
    }

    public void beginHeader(int level, WikiParameters params)
    {
        // No need for a marker since we expect only a single TextBlock
    }

    public void beginInfoBlock(char infoType, WikiParameters params)
    {
    }

    public void beginList(WikiParameters params, boolean ordered)
    {
        this.stack.push(this.marker);
    }

    public void beginListItem()
    {
        this.stack.push(this.marker);
    }

    public void beginParagraph(WikiParameters params)
    {
        this.stack.push(this.marker);
    }

    public void beginPropertyBlock(String propertyUri, boolean doc)
    {
        // TODO Auto-generated method stub

    }

    public void beginPropertyInline(String str)
    {
        // TODO Auto-generated method stub

    }

    public void beginQuotation(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void beginQuotationLine()
    {
        // TODO Auto-generated method stub

    }

    public void beginTable(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void beginTableCell(boolean tableHead, WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void beginTableRow(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void endDefinitionDescription()
    {
        // TODO Auto-generated method stub

    }

    public void endDefinitionList(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void endDefinitionTerm()
    {
        // TODO Auto-generated method stub

    }

    public void endDocument()
    {
        // TODO Auto-generated method stub

    }

    public void endFormat(WikiFormat format)
    {
        if (format.hasStyle(IWemConstants.STRONG)) {
          this.stack.push(new BoldBlock(generateListFromStack()));
        } else if (format.hasStyle(IWemConstants.EM)) {
            this.stack.push(new ItalicBlock(generateListFromStack()));
        } else {
            // WikiModel generate begin/endFormat events even for simple text with no style
            // so we need to remove our marker
            for (Block block: generateListFromStack()) {
                this.stack.push(block);
            }
        }
    }

    public void endHeader(int level, WikiParameters params)
    {
        this.stack.push(new SectionBlock(generateListFromStack(), SectionLevel.parseInt(level)));
    }

    public void endInfoBlock(char infoType, WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void endList(WikiParameters params, boolean ordered)
    {
        ListBLock listBlock;
        if (ordered) {
            listBlock = new NumberedListBlock(generateListFromStack());
        } else {
            listBlock = new BulletedListBlock(generateListFromStack());
        }
        this.stack.push(listBlock);
    }

    public void endListItem()
    {
        // Note: This means we support Paragraphs inside lists.
        this.stack.push(new ListItemBlock(generateListFromStack()));
    }

    public void endParagraph(WikiParameters params)
    {
        this.stack.push(new ParagraphBlock(generateListFromStack()));
    }

    public void endPropertyBlock(String propertyUri, boolean doc)
    {
        // TODO Auto-generated method stub

    }

    public void endPropertyInline(String inlineProperty)
    {
        // TODO Auto-generated method stub

    }

    public void endQuotation(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void endQuotationLine()
    {
        // TODO Auto-generated method stub

    }

    public void endTable(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void endTableCell(boolean tableHead, WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void endTableRow(WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void onEmptyLines(int count)
    {
        // TODO Auto-generated method stub

    }

    public void onEscape(String str)
    {
        // TODO Auto-generated method stub

    }

    public void onExtensionBlock(String extensionName, WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void onExtensionInline(String extensionName, WikiParameters params)
    {
        // TODO Auto-generated method stub

    }

    public void onHorizontalLine()
    {
        // TODO Auto-generated method stub

    }

    public void onLineBreak()
    {
        // TODO Auto-generated method stub
    }

    public void onMacroBlock(String macroName, WikiParameters params, String content)
    {
        Map<String, String> xwikiParams = new LinkedHashMap<String, String>();
        for (WikiParameter wikiParameter: params.toList()) {
            xwikiParams.put(wikiParameter.getKey(), wikiParameter.getValue());
        }
        
        this.stack.push(new MacroBlock(macroName, xwikiParams, content));
    }

    public void onMacroInline(String macroName, WikiParameters params, String content)
    {
        onMacroBlock(macroName, params, content);
    }

    public void onNewLine()
    {
        // TODO Auto-generated method stub

    }

    public void onReference(String ref, boolean explicitLink)
    {
        // TODO Auto-generated method stub

    }

    public void onSpace(String str)
    {
        this.stack.push(SpaceBlock.SPACE_BLOCK);
    }

    public void onSpecialSymbol(String symbol)
    {
        this.stack.push(new SpecialSymbolBlock(symbol));
    }

    public void onTableCaption(String str)
    {
        // TODO Auto-generated method stub

    }

    public void onVerbatimBlock(String str)
    {
        // TODO Auto-generated method stub

    }

    public void onVerbatimInline(String str)
    {
        // TODO Auto-generated method stub

    }

    public void onWord(String str)
    {
        this.stack.push(new WordBlock(str));
    }

    private List<Block> generateListFromStack() 
    {
        List<Block> blocks = new ArrayList<Block>();
        while (!this.stack.empty()) {
            if (this.stack.peek() != this.marker) {
                blocks.add(this.stack.pop());
            } else {
                this.stack.pop();
                break;
            }
        }
        Collections.reverse(blocks);
        return blocks;
    }
}
