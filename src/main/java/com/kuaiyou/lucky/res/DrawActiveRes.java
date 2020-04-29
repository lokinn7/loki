package com.kuaiyou.lucky.res;

import java.util.List;

import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.entity.Prizesetting;
import com.kuaiyou.lucky.entity.Userdraw;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
public class DrawActiveRes extends Draw {

	private List<Userdraw> userdraws;

	private List<Prizesetting> prizes;
}
