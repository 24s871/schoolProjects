library ieee;
use ieee.std_logic_1164.all;

entity plecat is
	port(ma,mb,ga,gb,sup,enable:in std_logic;
	alarma3:out std_logic);
end entity;

architecture cmp of plecat is
begin
	process(ma,mb,ga,gb,sup,enable)
	begin
		if(enable<='1') then
			if(ma='0' and mb='0' and ga='0' and gb='0' and sup='0') then
				alarma3<='0';
			else alarma3<='1';
			end if;
			else alarma3<='0';
			end if;
			end process;
		end architecture;
		