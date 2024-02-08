library ieee;
use ieee.std_logic_1164.all;

entity acasa is
	port(ma,mb,ga,gb,enable:in std_logic;
	alarma2:out std_logic);
end entity;

architecture cmp of acasa is
begin
	process(ma,mb,ga,gb,enable)
	begin
		if(enable<='1') then
			if(ma='0' and mb='0' and ga='0' and gb='0') then
				alarma2<='0';
			else alarma2<='1';
				end if;
			else alarma2<='0';
			end if;
			 end process;
			end architecture;